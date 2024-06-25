<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.prj.GSConstProject" %>

<!DOCTYPE html>

<%-- CMD定数 --%>
<%
  String okClick = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_OK_CLICK;
  String template = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_USE_TEMPLATE;
  String deleteClick = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_DEL_CLICK;
  String backClick = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_BACK_CLICK;
  String backRedraw = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_BACK_REDRAW;
  String prjStatusModify = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_PRJ_STATUS_MODIFY_CLICK;
  String memberAdd = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_MEMBER_ADD_CLICK;
  String memberRemove = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_MEMBER_REMOVE_CLICK;
  String adminAdd = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_ADMIN_ADD_CLICK;
  String adminRemove = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_ADMIN_REMOVE_CLICK;
  String categoriModify = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_CATEGORI_MODIFY_CLICK;
  String statusModify = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_STATUS_MODIFY_CLICK;
  String copyClick = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_COPY_CLICK;
  String memberEdit = jp.groupsession.v2.prj.prj020.Prj020Action.CMD_MEMBER_EDIT;
%>

<%-- 定数値 --%>
<%
  String entryAdd = jp.groupsession.v2.prj.GSConstProject.CMD_MODE_ADD;
  String entryEdit = jp.groupsession.v2.prj.GSConstProject.CMD_MODE_EDIT;
  String dspId = jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ020;
  String kengenMem = String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_EDIT_KENGEN_MEM);
  String kengenAdm = String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_EDIT_KENGEN_ADM);
  String kengenAll = String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_EDIT_KENGEN_ALL);
  String mailKbnFree = String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_MAIL_FREE);
  String mailKbnAdmin = String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_MAIL_SEND_ADMIN);
  String enabled = String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_KOUKAI_ENABLED);
  String disabled = String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_KOUKAI_DISABLED);
  String mode_tmp = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MODE_TMP_SELECT);
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
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>" ></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/project.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/prj020.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>

<body onload="showLengthId($('#inputstr')[0], <%=maxLengthTarget%>, 'inputlength');showLengthId($('#inputstr2')[0], <%=maxLengthContent%>, 'inputlength2');scroll();">

  <div id="FreezePane">

    <html:form action="/project/prj020">
      <input type="hidden" name="helpPrm" value="<bean:write name="prj020Form" property="prj020cmdMode" />">

      <input type="hidden" name="CMD" id="CMD" value="">
      <html:hidden property="prj010cmdMode" />
      <html:hidden property="prj010page1" />
      <html:hidden property="prj010page2" />
      <html:hidden property="prj010sort" />
      <html:hidden property="prj010order" />
      <html:hidden property="selectingProject" />
      <html:hidden property="selectingTodoDay" />
      <html:hidden property="selectingTodoPrj" />
      <html:hidden property="selectingTodoSts" />
      <html:hidden property="prj010Init" />

      <html:hidden property="prj020startYear" />
      <html:hidden property="prj020startMonth" />
      <html:hidden property="prj020startDay" />
      <html:hidden property="prj020endYear" />
      <html:hidden property="prj020endMonth" />
      <html:hidden property="prj020endDay" />

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

      <logic:notEmpty name="prj020Form" property="prj040scMemberSid" scope="request">
        <logic:iterate id="item" name="prj020Form" property="prj040scMemberSid" scope="request">
          <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="prj020Form" property="prj040svScMemberSid" scope="request">
        <logic:iterate id="item" name="prj020Form" property="prj040svScMemberSid" scope="request">
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
      <html:hidden property="selectingCategory" />
      <html:hidden property="selectingStatus" />
      <html:hidden property="selectingMember" />

      <logic:notEmpty name="prj020Form" property="prj030sendMember" scope="request">
        <logic:iterate id="item" name="prj020Form" property="prj030sendMember" scope="request">
          <input type="hidden" name="prj030sendMember" value="<bean:write name="item"/>">
        </logic:iterate>
      </logic:notEmpty>

      <html:hidden property="prj020scrId" />
      <html:hidden property="prj020cmdMode" />
      <html:hidden property="prj020prjSid" />
      <html:hidden property="prj020prjMyKbn" />
      <html:hidden property="copyProjectSid" value="" />

      <logic:notEmpty name="prj020Form" property="prj020hdnMember" scope="request">
        <logic:iterate id="item" name="prj020Form" property="prj020hdnMember" scope="request">
          <input type="hidden" name="prj020hdnMember" value="<bean:write name="item"/>">
        </logic:iterate>
      </logic:notEmpty>
      
      <logic:equal name="prj020Form" property="prj020prjMyKbn" value="<%= String.valueOf(GSConstProject.KBN_MY_PRJ_MY) %>">
        <logic:notEmpty name="prj020Form" property="prj020hdnAdmin" scope="request">
          <logic:iterate id="item" name="prj020Form" property="prj020hdnAdmin" scope="request">
            <input type="hidden" name="prj020hdnAdmin" value="<bean:write name="item"/>">
          </logic:iterate>
        </logic:notEmpty>
      </logic:equal>

      <html:hidden property="prjTmpMode" />
      <html:hidden property="movedDspId" />
      <html:hidden property="selectDir" />

      <logic:notEmpty name="prj020Form" property="prj150CompanySid">
        <logic:iterate id="coId" name="prj020Form" property="prj150CompanySid">
          <input type="hidden" name="prj150CompanySid" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="prj020Form" property="prj150CompanyBaseSid">
        <logic:iterate id="coId" name="prj020Form" property="prj150CompanyBaseSid">
          <input type="hidden" name="prj150CompanyBaseSid" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="prj020Form" property="prj150AddressIdSv">
        <logic:iterate id="addressId" name="prj020Form" property="prj150AddressIdSv">
          <input type="hidden" name="prj150AddressIdSv" value="<bean:write name="addressId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <input type="hidden" name="prj020UsrDel" value="">
      <html:hidden property="prj020EditDspFlg" />

      <html:hidden property="prj020AddMemAllDelFlg" />
      <html:hidden property="prj020ScrollFlg" />
      <html:hidden property="prj020IcoName" />
      <html:hidden property="prj020IcoSaveName" />
      <html:hidden property="prj020IcoSetFlg" />
      <html:hidden property="prj020initFlg" />

      <bean:define id="prjStsMdl" name="prj020Form" property="prjStatusMdl" />

      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

      <div class="pageTitle w80 mrl_auto">
        <ul>
          <li>
            <img class="header_pluginImg-classic" src="../project/images/classic/header_project.png" alt="<gsmsg:write key="cmn.project" />">
            <img class="header_pluginImg" src="../project/images/original/header_project.png" alt="<gsmsg:write key="cmn.project" />">
          </li>
          <li>
            <gsmsg:write key="cmn.project" />
          </li>
          <li class="pageTitle_subFont">
            <logic:equal name="prj020Form" property="prj020cmdMode" value="<%=entryAdd%>">
              <gsmsg:write key="project.131" />
            </logic:equal>
            <logic:equal name="prj020Form" property="prj020cmdMode" value="<%=entryEdit%>">
              <gsmsg:write key="project.132" />
            </logic:equal>
          </li>
          <li>
            <div>
              <logic:equal name="prj020Form" property="prj020cmdMode" value="<%=entryAdd%>">
                <button type="button" value="<gsmsg:write key="cmn.template" />" class="baseBtn" onclick="setDateParam();useTemplate('<%=template%>', '<%=mode_tmp%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_template.png" alt="<gsmsg:write key="cmn.template" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_template.png" alt="<gsmsg:write key="cmn.template" />">
                  <gsmsg:write key="cmn.template" />
                </button>
                <button type="button" value="<gsmsg:write key="cmn.register.copy2" />" class="baseBtn" onclick="return openpos();">
                   <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2"/>">
                   <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2"/>">
                   <gsmsg:write key="cmn.register.copy2" />
                  </button>
              </logic:equal>
              <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="prjButtonPush('<%=okClick%>');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                <gsmsg:write key="cmn.ok" />
              </button>
              <logic:equal name="prj020Form" property="prj020cmdMode" value="<%=entryEdit%>">
                <logic:equal name="prj020Form" property="prj020prjMyKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF)%>">
                  <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();prjButtonPush('<%=deleteClick%>');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <gsmsg:write key="cmn.delete" />
                  </button>
                </logic:equal>
              </logic:equal>
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

        <!-- 上テーブル -->
        <table class="table-left">
          <tr>
            <th class="w25 no_w">
              <span>
                <gsmsg:write key="project.31" />
              </span>
              <span class="cl_fontWarn">※</span>
            </th>
            <td class="txt_l w80">
              <html:text property="prj020prjId" styleClass="wp300" maxlength="20" />
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="project.40" />
              </span>
              <span class="cl_fontWarn">※</span>
            </th>
            <td class="txt_l">
              <html:text property="prj020prjName" styleClass="wp500" maxlength="70" />
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="project.41" />
              </span>
              <span class="cl_fontWarn">※</span>
            </th>
            <td class="txt_l">
              <html:text property="prj020prjNameS" styleClass="wp500" maxlength="20" />
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="project.10" />
              </span>
            </th>
            <td class="txt_l">
              <html:text property="prj020yosan" styleClass="wp150" maxlength="14" />
              <span class="ml5">
                <gsmsg:write key="project.103" />
              </span>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="project.19" />
              </span>
            </th>
            <td class="txt_l">
              <span class="verAlignMid">
              <html:radio property="prj020koukai" styleId="prj020KoukaiKbn_0" value="<%=enabled%>" />
                <label for="prj020KoukaiKbn_0">
                  <gsmsg:write key="cmn.public" />
                </label>
              <html:radio styleClass="ml10" property="prj020koukai" styleId="prj020KoukaiKbn_1" value="<%=disabled%>" />
                <label for="prj020KoukaiKbn_1">
                  <gsmsg:write key="cmn.private" />
                </label>
              </span>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="cmn.start" />
              </span>
            </th>
            <td class="txt_l">
              <span class="verAlignMid">
                <html:text name="prj020Form" property="prj020startDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatefr"/>
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
            <th class="no_w">
              <span>
                <gsmsg:write key="cmn.end" />
              </span>
            </th>
            <td class="txt_l">
              <span class="verAlignMid">
                <html:text name="prj020Form" property="prj020endDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateto"/>
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
            </td>
          </tr>

          <logic:equal name="prj020Form" property="prj020prjMyKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF)%>">
            <tr>
              <th class="no_w">
                <span>
                  <gsmsg:write key="cmn.status" />
                </span>
              </th>
              <td class="txt_l">
               <div>
                <button type="button" class="baseBtn" value="<gsmsg:write key="project.77" />" onclick="setDateParam();prjButtonPush('<%=prjStatusModify%>');"><gsmsg:write key="project.77" /></button>
                </div>
                <logic:notEmpty name="prjStsMdl" property="prjStatusList">
                  <logic:iterate id="prjStatus" name="prjStsMdl" property="prjStatusList">
                    <bean:define id="idbase" value="prj020status" />
                    <bean:define id="prsSid" name="prjStatus" property="prsSid" />
                    <bean:define id="idname" value="<%=String.valueOf(idbase) + String.valueOf(prsSid)%>" />
                    <span class="verAlignMid mr10">
                    <html:radio property="prj020status" styleId="<%=idname%>" value="<%=String.valueOf(prsSid)%>" />
                      <label for="<%=idname%>">
                        <bean:write name="prjStatus" property="prsRate" />%(<bean:write name="prjStatus" property="prsName" />)
                      </label>
                    </span>
                  </logic:iterate>
                </logic:notEmpty>
              </td>
            </tr>
          </logic:equal>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="project.21" />
              </span>
            </th>
            <td class="txt_l">
              <div>
                <textarea name="prj020mokuhyou" rows="5" class="wp500" onkeyup="showLengthStr(value, <%=maxLengthTarget%>, 'inputlength');" id="inputstr"><bean:write name="prj020Form" property="prj020mokuhyou" /></textarea>
              </div>
              <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>
              <span class="formCounter_max"> / <%=maxLengthTarget%><gsmsg:write key="cmn.character" />
              </span>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="cmn.content2" />
              </span>
            </th>
            <td class="txt_l">
              <div>
                <textarea name="prj020naiyou" rows="5" class="wp500" onkeyup="showLengthStr(value, <%=maxLengthContent%>, 'inputlength2');" id="inputstr2"><bean:write name="prj020Form" property="prj020naiyou" /></textarea>
              </div>
              <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength2" class="formCounter">0</span>
              <span class="formCounter_max"> / <%=maxLengthContent%><gsmsg:write key="cmn.character" />
              </span>
              <a id="add_user" name="add_user"></a>
            </td>
          </tr>

          <logic:equal name="prj020Form" property="prj020prjMyKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF)%>">
            <tr>
              <th class="no_w">
                <gsmsg:write key="cmn.squad" />
              </th>

              <td class="w75">
                <ui:multiselector name="prj020Form" property="prj020hdnMemberUI" styleClass="hp200" />
              </td>
            </tr>

            <logic:equal name="prj020Form" property="prj020cmdMode" value="0">
              <logic:notEmpty name="prj020Form" property="prj150AddressIdSv" scope="request">
                <tr>
                  <th class="no_w">
                    <span>
                      <gsmsg:write key="project.prj020.4" />
                    </span>
                  </th>
                  <td class="txt_l">
                    <logic:notEmpty name="prj020Form" property="prj020AddDataList">
                      <span class="verAlignMid">
                        <logic:iterate name="prj020Form" property="prj020AddDataList" scope="request" id="companyData">
                          <button class="iconBtn-border" onClick="deleteCompany(<bean:write name="companyData" property="adrSid" />);">
                            <img class="classic-display" src="../common/images/classic/icon_delete_2.gif" alt="<gsmsg:write key="cmn.delete.company" />">
                            <img class="original-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete.company" />">
                          </button>
                          <span>
                            <bean:write name="companyData" property="adrName" />
                          </span>
                          <br>
                        </logic:iterate>
                      </span>
                    </logic:notEmpty>
                  </td>
                </tr>
              </logic:notEmpty>
            </logic:equal>

            <logic:notEqual name="prj020Form" property="prj020cmdMode" value="0">
              <logic:notEmpty name="prj020Form" property="prj150AddressIdSv" scope="request">
                <tr>
                  <th class="no_w">
                    <span>
                      <gsmsg:write key="project.prj020.4" />
                    </span>
                  </th>
                  <td class="txt_l">
                    <logic:notEmpty name="prj020Form" property="prj020AddDataList">
                      <logic:iterate name="prj020Form" property="prj020AddDataList" scope="request" id="companyData">
                        <div class="mb5">
                            <img class="classic-display cursor_p" src="../common/images/classic/icon_delete_2.gif" class="img_bottom" alt="<gsmsg:write key="cmn.delete.company" />"  onClick="deleteCompany(<bean:write name="companyData" property="adrSid" />);">
                            <img class="original-display webIcon-Btn cursor_p" src="../common/images/original/icon_delete.png" class="img_bottom" alt="<gsmsg:write key="cmn.delete.company" />"  onClick="deleteCompany(<bean:write name="companyData" property="adrSid" />);">
                          <span class="mr5">
                            <bean:write name="companyData" property="adrName" />
                          </span>
                          <br>
                        </div>
                      </logic:iterate>
                    </logic:notEmpty>
                  </td>
                </tr>
              </logic:notEmpty>
            </logic:notEqual>

            <tr>
              <th class="no_w">
                <span>
                  <gsmsg:write key="project.src.32" />
                </span>
              </th>

              <td class="w75">
                <ui:multiselector name="prj020Form" property="prj020adminSelectUI" styleClass="hp200" onchange="" />
              </td>
            </tr>
          </logic:equal>

          <tr>
            <th class="no_w txt_c txt_l">
              <span>
                <gsmsg:write key="cmn.icon" />
              </span>
            </th>
            <td class="txt_l">
              <div class="txt_t">
                <logic:equal name="prj020Form" property="prj020IcoName" value="">
                  <img class="classic-display" src="../project/images/classic/icon_project.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />">
                  <img class="original-display" src="../project/images/original/plugin_project.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />">
                </logic:equal>
                <logic:notEqual name="prj020Form" property="prj020IcoName" value="">
                  <img src="../project/prj020.do?CMD=getImageFile&prj020cmdMode=<bean:write name="prj020Form" property="prj020cmdMode" />&prj020prjSid=<bean:write name="prj020Form" property="prj020prjSid" />" name="pitctImage" class="wp30hp30" alt="<gsmsg:write key="cmn.icon" />" border="1"
                    onload="initImageView('pitctImage');">
                </logic:notEqual>
                <div>
                  <span>
                    <gsmsg:write key="cmn.icon.size" />
                  </span>
                </div>
              </div>
              <attachmentFile:filearea
              mode="<%= String.valueOf(GSConstCommon.CMN110MODE_GAZOU) %>"
              pluginId="<%=GSConstProject.PLUGIN_ID_PROJECT %>"
              tempDirId="prj020"
              tempDirPlus="<%= GSConstProject.TEMP_ICN_PRJ %>"
              delBtn="true"
              delBtnAction="buttonPush('prj020tempdeleteMark');"
              fileList="false" />
            </td>
          </tr>
        </table>

        <!-- 下テーブル -->
        <div class="txt_l">
          <gsmsg:write key="project.14" />
        </div>

        <table class="table-left m0">
          <tr>
            <th class="w25 no_w">
              <span>
                <gsmsg:write key="cmn.label" />
              </span>
            </th>
            <td class="w75 txt_l">
              <button type="button" class="baseBtn" value="<gsmsg:write key="project.77" />" onclick="setDateParam();prjButtonPush('<%=categoriModify%>');"><gsmsg:write key="project.77" /></button>
              <div>
                <logic:notEmpty name="prjStsMdl" property="todoCateList">
                  <logic:iterate id="todoCate" name="prjStsMdl" property="todoCateList">
                    <div>
                      <bean:write name="todoCate" property="ptcName" />
                    </div>
                  </logic:iterate>
                </logic:notEmpty>
              </div>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="cmn.status" />
              </span>
            </th>
            <td class="txt_l">
              <button type="button" class="baseBtn" value="<gsmsg:write key="project.77" />" onclick="setDateParam();prjButtonPush('<%= statusModify %>');"><gsmsg:write key="project.77" /></button>
              <div>
                <logic:notEmpty name="prjStsMdl" property="todoStatusList">
                  <logic:iterate id="todoStatus" name="prjStsMdl" property="todoStatusList">
                    <div>
                      <bean:write name="todoStatus" property="ptsRate" />%(<bean:write name="todoStatus" property="ptsName" />)
                    </div>
                  </logic:iterate>
                </logic:notEmpty>
              </div>
            </td>
          </tr>

          <logic:equal name="prj020Form" property="prj020prjMyKbn" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF) %>">
            <tr>
              <th class="no_w">
                <span>
                  <gsmsg:write key="cmn.edit.permissions" />
                </span>
              </th>
              <td class="txt_l">
                <span class="verAlignMid mr10">
                <html:radio property="prj020kengen" styleId="edit0" value="<%= kengenMem %>" />
                  <label for="edit0">
                    <gsmsg:write key="project.prj020.8" />
                  </label>
                </span>
                <span class="verAlignMid mr10">
                <html:radio property="prj020kengen" styleId="edit1" value="<%= kengenAdm %>" />
                  <label for="edit1">
                    <gsmsg:write key="project.13" />
                  </label>
                </span>
                <span class="verAlignMid">
                <html:radio property="prj020kengen" styleId="edit2" value="<%= kengenAll %>" />
                  <label for="edit2">
                    <gsmsg:write key="cmn.no.setting.permission" />
                  </label>
                </span>
              </td>
            </tr>
          </logic:equal>

          <logic:equal name="prj020Form" property="useSmail" value="true">
            <tr>
              <th class="no_w">
                <span>
                  <gsmsg:write key="project.2" />
                </span>
              </th>
              <td class="txt_l">
                <span class="verAlignMid">
                <html:radio property="prj020smailKbn" styleId="mailKbn0" value="<%= mailKbnFree %>" />
                  <label class="mr10" for="mailKbn0">
                    <gsmsg:write key="project.16" />
                  </label>
                <html:radio property="prj020smailKbn" styleId="mailKbn1" value="<%= mailKbnAdmin %>" />
                  <label for="mailKbn1">
                    <gsmsg:write key="project.17" />
                  </label>
                </span>
              </td>
            </tr>
          </logic:equal>

        </table>

        <div class="footerBtn_block mt5">
          <logic:equal name="prj020Form" property="prj020cmdMode" value="<%=entryAdd%>">
                <button type="button" value="<gsmsg:write key="cmn.template" />" class="baseBtn" onclick="setDateParam();useTemplate('<%=template%>', '<%=mode_tmp%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_template.png" alt="<gsmsg:write key="cmn.template" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_template.png" alt="<gsmsg:write key="cmn.template" />">
                  <gsmsg:write key="cmn.template" />
                </button>
                <button type="button" value="<gsmsg:write key="cmn.register.copy2" />" class="baseBtn" onclick="return openpos();">
                   <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2"/>">
                   <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2"/>">
                   <gsmsg:write key="cmn.register.copy2" />
                  </button>
              </logic:equal>
          <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="prjButtonPush('<%=okClick%>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>
          <logic:equal name="prj020Form" property="prj020cmdMode" value="<%=entryEdit%>">
            <logic:equal name="prj020Form" property="prj020prjMyKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF)%>">
              <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();prjButtonPush('<%=deleteClick%>');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:equal>
          </logic:equal>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="prjButtonPush('<%=backClick%>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </div>

      <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

      <div id="subPanel" class="display_n txt_c p0">
        <div class="bd h100 ofy_h">
          <iframe src="../common/html/damy.html" name="pos" class="m0 p0 w100 h100"></iframe>
        </div>
      </div>

    </html:form>
  </div>
</body>
</html:html>
