<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>

<!DOCTYPE html>

<%-- CMD定数 --%>
<%
  String okClick = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_OK_CLICK;
  String deleteClick = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_DEL_CLICK;
  String backClick = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_BACK_CLICK;
  String backRedraw = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_BACK_REDRAW;
  String prjStatusModify = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_PRJ_STATUS_MODIFY_CLICK;
  String memberAdd = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_MEMBER_ADD_CLICK;
  String memberRemove = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_MEMBER_REMOVE_CLICK;
  String adminAdd = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_ADMIN_ADD_CLICK;
  String adminRemove = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_ADMIN_REMOVE_CLICK;
  String categoriModify = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_CATEGORI_MODIFY_CLICK;
  String statusModify = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_STATUS_MODIFY_CLICK;
  String memberEdit = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_MEMBER_EDIT;
%>

<%-- 定数値 --%>
<%
  String mode_kojin = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MODE_TMP_KOJIN);
  String mode_kyoyu = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MODE_TMP_KYOYU);
  String mode_select = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MODE_TMP_SELECT);
  String kengenMem = String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_EDIT_KENGEN_MEM);
  String kengenAdm = String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_EDIT_KENGEN_ADM);
  String kengenAll = String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_EDIT_KENGEN_ALL);
  String mailKbnFree = String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_MAIL_FREE);
  String mailKbnAdmin = String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_MAIL_SEND_ADMIN);
  String enabled = String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_KOUKAI_ENABLED);
  String disabled = String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_KOUKAI_DISABLED);
  String dspId = jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ140;
  String maxLengthTarget = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MAX_LENGTH_TARGET);
  String maxLengthContent = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MAX_LENGTH_CONTENT);
%>

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
<script src="../project/js/prj140.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel=stylesheet href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>

<body onload="showLengthId($('#inputstr')[0], <%=maxLengthTarget%>, 'inputlength');showLengthId($('#inputstr2')[0], <%=maxLengthContent%>, 'inputlength2');">

  <html:form action="/project/prj140">
    <input type="hidden" name="helpPrm" value="<bean:write name="prj140Form" property="prj140cmdMode" />">
    <input type="hidden" name="CMD" id="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="prj010cmdMode" />
    <html:hidden property="prj010sort" />
    <html:hidden property="prj010order" />
    <html:hidden property="prj010page1" />
    <html:hidden property="prj010page2" />
    <html:hidden property="prj010Init" />
    <html:hidden property="selectingProject" />
    <html:hidden property="selectingTodoDay" />
    <html:hidden property="selectingTodoPrj" />
    <html:hidden property="selectingTodoSts" />
    <html:hidden property="prjTmpMode" />
    <html:hidden property="prtSid" />
    <html:hidden property="movedDspId" />
    <html:hidden property="prj140cmdMode" />
    <html:hidden property="prj140startYear" />
    <html:hidden property="prj140startMonth" />
    <html:hidden property="prj140startDay" />
    <html:hidden property="prj140endYear" />
    <html:hidden property="prj140endMonth" />
    <html:hidden property="prj140endDay" />

    <logic:notEmpty name="prj140Form" property="prj140hdnMember" scope="request">
      <logic:iterate id="item" name="prj140Form" property="prj140hdnMember" scope="request">
        <input type="hidden" name="prj140hdnMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj140Form" property="prj150AddressIdSv" scope="request">
      <logic:iterate id="addressId" name="prj140Form" property="prj150AddressIdSv" scope="request">
        <input type="hidden" name="prj150AddressIdSv" value="<bean:write name="addressId"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj140Form" property="prj150CompanySid" scope="request">
      <logic:iterate id="coId" name="prj140Form" property="prj150CompanySid" scope="request">
        <input type="hidden" name="prj150CompanySid" value="<bean:write name="coId"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj140Form" property="prj150CompanyBaseSid" scope="request">
      <logic:iterate id="coId" name="prj140Form" property="prj150CompanyBaseSid" scope="request">
        <input type="hidden" name="prj150CompanyBaseSid" value="<bean:write name="coId"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj140Form" property="prjStatusTmpMdl" scope="request">
      <bean:define id="prjStsMdl" name="prj140Form" property="prjStatusTmpMdl"  />
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>

        <logic:equal name="prj140Form" property="prjTmpMode" value="<%=mode_kyoyu%>">
          <li>
            <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
          </li>

          <li>
            <gsmsg:write key="cmn.admin.setting" />
          </li>
          <li class="pageTitle_subFont">
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.project" /></span><logic:lessEqual name="prj140Form" property="prtSid" value="0"><gsmsg:write key="cmn.entry.shared.template" /></logic:lessEqual><logic:greaterThan name="prj140Form" property="prtSid" value="0"><gsmsg:write key="project.prj140.2" /></logic:greaterThan>
          </li>

        </logic:equal>

        <logic:equal name="prj140Form" property="prjTmpMode" value="<%=mode_kojin%>">
          <li>
            <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
          <li>
            <gsmsg:write key="cmn.preferences2" />
          </li>
          <li class="pageTitle_subFont">
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.project" /></span><logic:lessEqual name="prj140Form" property="prtSid" value="0"><gsmsg:write key="cmn.entry.personal.template" /></logic:lessEqual><logic:greaterThan name="prj140Form" property="prtSid" value="0"><gsmsg:write key="project.prj140.4" /></logic:greaterThan>
          </li>
        </logic:equal>

        <logic:equal name="prj140Form" property="prjTmpMode" value="<%= mode_select %>">
          <li>
            <img class="header_pluginImg-classic" src="../project/images/classic/header_project.png" alt="<gsmsg:write key="cmn.project" />">
            <img class="header_pluginImg" src="../project/images/original/header_project.png" alt="<gsmsg:write key="cmn.project" />">
          </li>
           <li class="pageTitle_subFont">
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.project" /></span><gsmsg:write key="project.prj140.5" />
          </li>
          </logic:equal>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="prjButtonPush('<%=okClick%>');">
               <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
               <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <logic:greaterThan name="prj140Form" property="prtSid" value="0">
              <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();prjButtonPush('<%=deleteClick%>');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:greaterThan>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="prjButtonPush('<%=backClick%>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <!-- 上テーブル  -->
      <table class="table-left" >
        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="project.prj140.6" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_l w75">
            <html:text property="prj140prtTmpName" styleClass="wp500" maxlength="50" />
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="project.31" />
          </th>
          <td class="txt_l" width="80%">
            <html:text property="prj140prtId" styleClass="wp300" maxlength="20" />
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="project.40" />
          </th>
          <td class="txt_l">
            <html:text property="prj140prtName" styleClass="wp500" maxlength="70" />
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="project.41" />
          </th>
          <td class="txt_l">
            <html:text property="prj140prtNameS" styleClass="wp300" maxlength="20" />
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="project.10" />
          </th>
          <td class="txt_l">
            <html:text property="prj140yosan" styleClass="wp100 mr5" maxlength="9" /><gsmsg:write key="project.103" />
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="project.19" />
            </span>
          </th>
          <td class="txt_l">
            <span class="verAlignMid">
            <html:radio property="prj140koukai" styleId="prj140koukai_0" value="<%=enabled%>" />
              <label for="prj140koukai_0">
                <gsmsg:write key="cmn.public" />
              </label>
            <html:radio property="prj140koukai" styleClass="ml10" styleId="prj140koukai_1" value="<%=disabled%>" />
              <label for="prj140koukai_1">
                <gsmsg:write key="cmn.private" />
              </label>
            </span>
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="cmn.start" />
          </th>
          <td class="txt_l">
            <span class="verAlignMid">
              <html:text name="prj140Form" property="prj140startDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatefr"/>
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
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.end" />
            </span>
          </th>
          <td class="txt_l">
            <span class="verAlignMid">
              <html:text name="prj140Form" property="prj140endDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateto"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="return moveDay($('#selDateto')[0],  1)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left "></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="return moveDay($('#selDateto')[0], 2)"">
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
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.status" />
            </span>
          </th>
          <td class="txt_l">
            <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="project.77" />" onclick="setDateParam();prjButtonPush('<%=prjStatusModify%>');">
              <gsmsg:write key="project.77" />
            </button>
            </div>
            <logic:notEmpty name="prjStsMdl">
            <logic:notEmpty name="prjStsMdl" property="prjStatusList">
              <span class="verAlignMid">
                <logic:iterate id="prjStatus" name="prjStsMdl" property="prjStatusList">
                  <bean:define id="idbase" value="prj140status" />
                  <bean:define id="pttSid" name="prjStatus" property="pttSid" />
                  <bean:define id="idname" value="<%=String.valueOf(idbase) + String.valueOf(pttSid)%>" />
                  <html:radio property="prj140status" styleId="<%=idname%>" value="<%=String.valueOf(pttSid)%>" />
                    <label class="mr10" for="<%=idname%>">
                      <bean:write name="prjStatus" property="pttRate" />%(<bean:write name="prjStatus" property="pttName" />)
                    </label>
                </logic:iterate>
              </span>
            </logic:notEmpty>
            </logic:notEmpty>
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="project.21" />
            </span>
          </th>
          <td class="txt_l">
            <textarea name="prj140mokuhyou" rows="5" class="wp500" onkeyup="showLengthStr(value, <%=maxLengthTarget%>, 'inputlength');" id="inputstr"><bean:write name="prj140Form" property="prj140mokuhyou" /></textarea>
            <div>
              <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>
              <span class="formCounter_max"> / <%=maxLengthTarget%><gsmsg:write key="cmn.character" /></span>
            </div>
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.content2" />
            </span>
          </th>
          <td class="txt_l">
            <textarea name="prj140naiyou" rows="5" class="wp500" onkeyup="showLengthStr(value, <%=maxLengthContent%>, 'inputlength2');" id="inputstr2"><bean:write name="prj140Form" property="prj140naiyou" /></textarea>
            <div>
              <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength2" class="formCounter">0</span>
              <span class="formCounter_max"> / <%=maxLengthContent%><gsmsg:write key="cmn.character" /></span>
            </div>
          </td>
        </tr>
        <!-- 所属メンバー -->
        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.squad" />
            </span>
          </th>

          </th>
          <td class="w75">
            <ui:multiselector name="prj140Form" property="prj140hdnMemberUI" styleClass="hp200" />
            <div>
              <button type="button" value="<gsmsg:write key="project.1" />" class="baseBtn" onclick="memberEdit('<%=memberEdit%>', '<%=dspId%>');">
                <gsmsg:write key="project.1" />
              </button>
            </div>
          </td>
        </tr>
        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="project.src.32" />
            </span>
          </th>

          </th>
          <td class="w75">
              <ui:multiselector name="prj140Form" property="prj140adminSelectUI" styleClass="hp200"
               onchange=""
               />
          </td>

        </tr>
      </table>

      <div class="txt_l fs_15">
        <gsmsg:write key="project.14" />
      </div>

      <table class="table-left m0" >
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="cmn.label" />
          </th>
          <td class="txt_l w75">
            <button type="button" class="baseBtn" value="<gsmsg:write key="project.77" />" onclick="setDateParam();prjButtonPush('<%=categoriModify%>');">
              <gsmsg:write key="project.77" />
            </button>
            <div>
              <logic:notEmpty name="prjStsMdl">
              <logic:notEmpty name="prjStsMdl" property="todoCateList">
                <logic:iterate id="todoCate" name="prjStsMdl" property="todoCateList">
                  <div>
                    <bean:write name="todoCate" property="pctName" />
                  </div>
                </logic:iterate>
              </logic:notEmpty>
              </logic:notEmpty>
            </div>
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.status" />
            </span>
          </th>
          <td class="w75 txt_l">
            <button type="button" class="baseBtn" value="<gsmsg:write key="project.77" />" onclick="setDateParam();prjButtonPush('<%=statusModify%>');">
              <gsmsg:write key="project.77" />
            </button>
            <div>
              <logic:notEmpty name="prjStsMdl">
              <logic:notEmpty name="prjStsMdl" property="todoStatusList">
                <logic:iterate id="todoStatus" name="prjStsMdl" property="todoStatusList">
                  <div>
                    <bean:write name="todoStatus" property="pstRate" />%(<bean:write name="todoStatus" property="pstName" />)
                  </div>
                </logic:iterate>
              </logic:notEmpty>
              </logic:notEmpty>
            </div>
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.edit.permissions" />
            </span>
          </th>
          <td class="w75 txt_l ">
            <span class="verAlignMid">
            <html:radio property="prj140kengen" styleId="edit0" value="<%=kengenMem%>" />
              <label for="edit0">
                <gsmsg:write key="project.prj020.8" />
              </label>
            </span>
            <span class="verAlignMid">
            <html:radio property="prj140kengen" styleClass="ml10" styleId="edit1" value="<%=kengenAdm%>" />
              <label for="edit1">
                <gsmsg:write key="project.13" />
              </label>
            </span>
            <span class="verAlignMid">
            <html:radio property="prj140kengen" styleClass="ml10" styleId="edit2" value="<%=kengenAll%>" />
              <label for="edit2">
                <gsmsg:write key="cmn.no.setting.permission" />
              </label>
            </span>
          </td>
        </tr>

        <logic:equal name="prj140Form" property="useSmail" value="true">
          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="project.2" />
              </span>
            </th>
            <td class="txt_l">
              <span class="verAlignMid">
              <html:radio property="prj140smailKbn" styleId="mailKbn0" value="<%= mailKbnFree %>" />
                <label for="mailKbn0">
                  <gsmsg:write key="project.16" />
                </label>
              </span>
              <span class="verAlignMid">
              <html:radio property="prj140smailKbn" styleClass="ml10" styleId="mailKbn1" value="<%= mailKbnAdmin %>" />
                <label for="mailKbn1">
                  <gsmsg:write key="project.17" />
                </label>
              </span>
            </td>
          </tr>
        </logic:equal>
      </table>

      <div class="footerBtn_block mt5">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="prjButtonPush('<%=okClick%>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:greaterThan name="prj140Form" property="prtSid" value="0">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();prjButtonPush('<%=deleteClick%>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:greaterThan>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="prjButtonPush('<%=backClick%>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>

  </div>

</body>
</html:html>