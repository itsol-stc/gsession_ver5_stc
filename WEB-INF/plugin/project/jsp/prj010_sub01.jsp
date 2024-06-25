<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%
  String cmdDelTo = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_DEL_TODO;
%>

<html:hidden property="selectingProject" />
<gsmsg:define id="projectTitle" msgkey="cmn.project" />
<gsmsg:define id="title" msgkey="cmn.title" />
<gsmsg:define id="todoNum" msgkey="project.prj050.5" />
<gsmsg:define id="todoWeight" msgkey="project.prj050.4" />
<gsmsg:define id="todoStatus" msgkey="cmn.status" />
<gsmsg:define id="todoStartPlan" msgkey="project.100" />
<gsmsg:define id="todoLimitPlan" msgkey="project.src.66" />

<%
  int[] sortKeyList01 = new int[] {
      jp.groupsession.v2.prj.GSConstProject.SORT_PROJECT_TITLE,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_TITLE,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_NO,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_WEIGHT,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_STATUS,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_START_PLAN,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_LIMIT_PLAN
  };
  String[] title_width01 = new String[] { "w20", "w30", "0", "w10", "w10", "w10", "w10" };
  String[] titleList01 = new String[] {
      projectTitle,
      title,
      todoNum,
      todoWeight,
      todoStatus,
      todoStartPlan,
      todoLimitPlan
  };
%>
<div>

  <table class="table-left m0 mb5 bgC_lightGray border_top_none">
    <tr>
      <td>
        <div class="component_bothEnd">
          <div>
            <span>
              <gsmsg:write key="cmn.date2" />
            </span>
            <logic:notEmpty name="prj010Form" property="targetTodoDayLabel">
              <html:select property="selectingTodoDay" onchange="return buttonPush('changeCombo');">
                <html:optionsCollection name="prj010Form" property="targetTodoDayLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>

            <span>
              <gsmsg:write key="cmn.project" />
            </span>
            <logic:notEmpty name="prj010Form" property="targetTodoProjectLabel">
              <html:select styleClass="wp180" property="selectingTodoPrj" onchange="return changeTodoPrj();">
                <html:optionsCollection name="prj010Form" property="targetTodoProjectLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>

            <span>
              <gsmsg:write key="cmn.status" />
            </span>
            <logic:notEmpty name="prj010Form" property="targetTodoStsLabel">
              <html:select property="selectingTodoSts" onchange="return buttonPush('changeCombo')">
                <html:optionsCollection name="prj010Form" property="targetTodoStsLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </div>
          <div>
            <button type="button" name="btn_prjadd" class="baseBtn no_w" value="<gsmsg:write key="cmn.advanced.search" />" onclick="buttonPush('<%=todoSearch%>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_advanced_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
              <gsmsg:write key="cmn.advanced.search" />
            </button>
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>


<div class="txt_r">

<div class="display_inline-block">
  <logic:notEmpty name="prj010Form" property="projectList" scope="request">
    <span class="cursor_p" onClick="return doOpenDialog();">
      <span>
        ▼<gsmsg:write key="project.date.change" />
      </span>
    </span>
    <span>
      <gsmsg:write key="project.20" />:
    </span>
    <html:select property="prj010selectEditStatus" >
      <html:optionsCollection name="prj010Form" property="editStatusLabel" value="value" label="label" />
    </html:select>
    <button type="button" value="<gsmsg:write key="cmn.change" />" class="baseBtn" onclick="buttonPush('<%=jp.groupsession.v2.prj.prj010.Prj010Action.CMD_EDIT_STATUS%>');">
      <gsmsg:write key="cmn.change" />
    </button>
  </logic:notEmpty>
  <button type="button" name="btn_prjadd" class="baseBtn" value="<gsmsg:write key="cmn.new.registration" />" onclick="buttonPushAdd('<%=todoAdd%>', <%=mode_add%>);">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.new.registration" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.new.registration" />">
    <gsmsg:write key="cmn.new.registration" />
  </button>
  <logic:notEmpty name="prj010Form" property="projectList" scope="request">
    <button type="button" name="btn_tododel" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('<%=cmdDelTo%>');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </logic:notEmpty>
  </div>

  <!-- ページング -->
  <div class="display_inline-block">
  <bean:size id="count1" name="prj010Form" property="pageLabel" scope="request" />
  <logic:greaterThan name="count1" value="1">

  <div class="paging">
    <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select styleClass="paging_combo"  property="prj010page1" onchange="changePage(1)">
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

<table class="table-top m0 mt5 mb5">
  <logic:notEmpty name="prj010Form" property="projectList" scope="request">
    <tr>
      <th class="w5 js_tableTopCheck js_tableTopCheck-header cursor_p">
        <input type="checkbox" name="prj010AllCheck" value="1" onClick="chgCheckAll('prj010AllCheck', 'prj010selectTodo');chgCheckAllChange('prj010AllCheck', 'prj010selectTodo');">
      </th>
      <%
          for (int i = 0; i < sortKeyList01.length; i++) {
              if (i != 2) {
        %>
      <th class="<%=title_width01[i]%> cursor_p no_w">
        <%
            } else {
          %>
        <span class="ml5 mr5">/</span>
        <%
            }
                if (iSortKbn == sortKeyList01[i]) {
                  if (iOrderKey == order_desc) {
          %>
        <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_asc%>);">
          <span><%=titleList01[i]%><span class="classic-display">▼</span>
            <span class="original-display">
              <i class="icon-sort_down"></i>
            </span>
          </span>
        </a>
        <%
            } else {
          %>
        <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_desc%>);">
          <span><%=titleList01[i]%><span class="classic-display">▲</span>
            <span class="original-display">
              <i class="icon-sort_up"></i>
            </span>
          </span>
        </a>
        <%
            }
                } else {
          %>
        <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_asc%>);">
          <span><%=titleList01[i]%></span>
        </a>
        <%
            }

                if (i != 1) {
          %>
      </th>
      <%
          }
            }
        %>
    </tr>

    <logic:iterate id="prjMdl" name="prj010Form" property="projectList" scope="request" indexId="idx">

      <logic:equal name="prjMdl" property="prjTodoEdit" value="true">
        <tr class="checkSelected n<bean:write name="prjMdl" property="projectSid" /><bean:write name="prjMdl" property="todoSid" />" id="<bean:write name="prjMdl" property="projectSid" />:<bean:write name="prjMdl" property="todoSid" />">
      </logic:equal>
      <logic:notEqual name="prjMdl" property="prjTodoEdit" value="true">
      <tr>
      </logic:notEqual>
      <td class="txt_c js_tableTopCheck judgCheck cursor_p">
        <logic:equal name="prjMdl" property="prjTodoEdit" value="true">

          <html:multibox name="prj010Form" property="prj010selectTodo">
            <bean:write name="prjMdl" property="projectSid" />:<bean:write name="prjMdl" property="todoSid" />
          </html:multibox>

        </logic:equal>
      </td>
      <td class="txt_l">
        <logic:equal name="prjMdl" property="prjBinSid" value="0">
          <img class="classic-display" src="../project/images/classic/icon_project.png" alt="<gsmsg:write key="cmn.icon" />">
          <img class="original-display" src="../project/images/original/plugin_project.png" alt="<gsmsg:write key="cmn.icon" />">
        </logic:equal>
        <logic:notEqual name="prjMdl" property="prjBinSid" value="0">
          <img src="../project/prj010.do?CMD=getImageFile&prj010PrjSid=<bean:write name="prjMdl" property="projectSid" />&prj010PrjBinSid=<bean:write name="prjMdl" property="prjBinSid" />" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />"
            onload="initImageView('pitctImage');" class="wp30hp30">
        </logic:notEqual>
        <a href="#!" onClick="return viewPoject('<%=prjMain%>', '<bean:write name="prjMdl" property="projectSid" />');">
          <span>
            <bean:write name="prjMdl" property="projectName" />
          </span>
        </a>
      </td>
      <td class="txt_l">
        <table>
          <tr>
            <td class="w90 border_none">
              <logic:equal name="prjMdl" property="keikoku" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KEIKOKU_ARI)%>">
                <logic:notEqual name="prjMdl" property="rate" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.RATE_MAX)%>">
                  <img class="classic-display" src="../common/images/classic/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
                  <img class="original-display" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
                </logic:notEqual>
              </logic:equal>
              <a href="#!" onClick="return viewTodo('<%=todoRef%>', '<bean:write name="prjMdl" property="projectSid" />', '<bean:write name="prjMdl" property="todoSid" />');">
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
            <td class="w3 txt_r txt_m no_w border_none">
              <logic:notEqual name="prjMdl" property="prjTodoCommentCnt" value="0">
                <a href="#!" onClick="return viewTodoComment('<%=todoRef%>', '<bean:write name="prjMdl" property="projectSid" />', '<bean:write name="prjMdl" property="todoSid" />');">
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
              <span>
                <bean:write name="prjMdl" property="strKanriNo" />
              </span>
            </td>
          </tr>
        </table>

      </td>

      <%-- <td class="txt_c"><span ><bean:write name="prjMdl" property="strJuyo" /></span></td> --%>
      <td class="txt_c">
        <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_LOW)%>">
          <img class="classic-display" src="../common/images/classic/icon_star_blue.png"  alt="<gsmsg:write key="project.58" />">
          <img class="classic-display" src="../common/images/classic/icon_star_white.png"  alt="<gsmsg:write key="project.58" />">
          <img class="classic-display" src="../common/images/classic/icon_star_white.png"  alt="<gsmsg:write key="project.58" />">
          <span class="original-display">
            <i class="icon-star importance-blue"></i>
            <i class="icon-star_line"></i>
            <i class="icon-star_line"></i>
          </span>
        </logic:equal>
        <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_MIDDLE)%>">
          <img class="classic-display" src="../common/images/classic/icon_star_gold.png"  alt="<gsmsg:write key="project.59" />">
          <img class="classic-display" src="../common/images/classic/icon_star_gold.png"  alt="<gsmsg:write key="project.59" />">
          <img class="classic-display" src="../common/images/classic/icon_star_white.png"  alt="<gsmsg:write key="project.59" />">
          <span class="original-display">
            <i class="icon-star importance-yellow"></i>
            <i class="icon-star importance-yellow"></i>
            <i class="icon-star_line"></i>
          </span>
        </logic:equal>
        <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_HIGH)%>">
          <img class="classic-display" src="../common/images/classic/icon_star_red.png"  alt="<gsmsg:write key="project.60" />">
          <img class="classic-display" src="../common/images/classic/icon_star_red.png"  alt="<gsmsg:write key="project.60" />">
          <img class="classic-display" src="../common/images/classic/icon_star_red.png"  alt="<gsmsg:write key="project.60" />">
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
      <td class="txt_c">
        <span>
          <bean:write name="prjMdl" property="strStartDate" />
        </span>
      </td>
      <td class="txt_c">
        <span>
          <bean:write name="prjMdl" property="strEndDate" />
        </span>
      </td>
      </tr>

    </logic:iterate>
  </logic:notEmpty>

</table>

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
            <span class="fs_13"><b><gsmsg:write key="cmn.select.3" arg0="TODO" /></b></span>
          </td>
        </tr>
      </table>
</div>