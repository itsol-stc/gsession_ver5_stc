<%@page import="jp.groupsession.v2.rsv.rsv010.Rsv010Form"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/reserve" prefix="reserve" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.rsv.rsv310.Rsv310Form"%>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page import="jp.groupsession.v2.usr.UserUtil"%>
<%@ page import="jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel"%>
<%
String maxLengthNaiyo = String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.MAX_LENGTH_NAIYO);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [ <gsmsg:write key="reserve.rsv210.1" /> ]
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../reserve/js/rsvschedule.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../reserve/js/sisetuPopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../reserve/js/rsv210.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/reservepopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jtooltip.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>" type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

</head>

<body class="body_03" onunload="windowClose();" onload="showLengthId($('#inputstr')[0], <%=maxLengthNaiyo%>, 'inputlength');rsvSchChange();">
  <html:form action="/reserve/rsv210">
    <input type="hidden" name="CMD" value="ikkatu_toroku">
    <html:hidden property="rsvBackPgId" />
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedGrpSid" />
    <html:hidden property="rsvSelectedSisetuSid" />
    <html:hidden property="rsv210YrkFrom" />
    <html:hidden property="rsv210SelectedHourFr" />
    <html:hidden property="rsv210SelectedHourTo" />
    <html:hidden property="rsv210SelectedMinuteFr" />
    <html:hidden property="rsv210SelectedMinuteTo" />
    <input type="hidden" name="hourDivision" value="<bean:write name="rsv210Form" property="rsv210HourDiv" />" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp"%>

    <logic:notEmpty name="rsv210Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv210Form" property="rsv100CsvOutField" scope="request">
        <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="rsv210InitFlg" />
    <logic:notEmpty name="rsv210Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv210Form" property="rsvIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv210Form" property="rsv210SchNotAccessGroupList" scope="request">
      <logic:iterate id="notAccessGroup" name="rsv210Form" property="rsv210SchNotAccessGroupList">
        <input type="hidden" name="rsvSchNotAccessGroup" value="<bean:write name="notAccessGroup" />">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
          <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
        </li>
        <li>
          <gsmsg:write key="cmn.reserve" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="reserve.rsv210.1" />
        </li>
        <li>
          <div>
            <button type="submit" value="<gsmsg:write key="cmn.ok" />" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_menu');">
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
      <table class="table-left rsv210_table table-fixed table bgC_none">
        <colgroup class="w20"></colgroup>
        <colgroup class="w80 bgC_tableCell"></colgroup>
        <tr>
          <th class="no_w w20">
            <span>
              <gsmsg:write key="reserve.122" />
            </span>
          </td>
          <td class="txt_l w80">
            <bean:define id="thisForm" type="Rsv010Form" name="rsv210Form"></bean:define>
            <reserve:rsvIkkatuHiddenList_body thisForm="<%=thisForm%>" useDelBtn="false"></reserve:rsvIkkatuHiddenList_body>
          </td>
        </tr>
        <tr class="">
          <td colspan="2" class="border_left_none border_right_none bgC_none"></td>
        </tr>

      <!-- 下テーブル -->
        <tr>
          <th class="no_w w20">
            <span>
              <gsmsg:write key="cmn.registant" />
            </span>
          </th>
          <td class="txt_l w80">
            <span>
              <bean:write name="rsv210Form" property="rsv210Torokusya" />
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w w20">
            <span>
              <gsmsg:write key="reserve.72" />
            </span>
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="txt_l w80">
            <html:text name="rsv210Form" property="rsv210Mokuteki" style="width:333px;" maxlength="50" />
          </td>
        </tr>

        <tr>
          <th class="no_w w20">
            <span>
              <gsmsg:write key="cmn.time" />
            </span>
          </th>
          <td class="txt_l w80">
            <div class="verAlignMid">
              <span><gsmsg:write key="cmn.start" /><gsmsg:write key="cmn.colon" /></span><!--
           --><span class="clockpicker_fr ml5 pos_rel display_flex input-group">
                <html:text name="rsv210Form" property="rsv210TimeFr" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
                <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
              </span>
            </div>
            <br>
            <div class="mt5 verAlignMid">
              <span><gsmsg:write key="cmn.end" /><gsmsg:write key="cmn.colon" /></span><!--
           --><span class="clockpicker_fr ml5 pos_rel display_flex input-group">
                <html:text name="rsv210Form" property="rsv210TimeTo" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
                <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
              </span>
            </div>
            <!-- 時間マスタ -->
            <div class="mt5 cl_linkDef">
              <span>
                <a href="#!" onclick="setAmTime();">
                  <gsmsg:write key="cmn.am" />
                  <span class="tooltips">
                    <bean:write name="rsv210Form" property="rsv210AmFrHour" format="00" />:
                    <bean:write name="rsv210Form" property="rsv210AmFrMin" format="00" />～
                    <bean:write name="rsv210Form" property="rsv210AmToHour" format="00" />:
                    <bean:write name="rsv210Form" property="rsv210AmToMin" format="00" />
                  </span>
                </a>
              </span>
              &nbsp;&nbsp;
              <span>
                <a href="#!" onclick="setPmTime();">
                  <gsmsg:write key="cmn.pm" />
                  <span class="tooltips">
                    <bean:write name="rsv210Form" property="rsv210PmFrHour" format="00" />:
                    <bean:write name="rsv210Form" property="rsv210PmFrMin" format="00" />～
                    <bean:write name="rsv210Form" property="rsv210PmToHour" format="00" />:
                    <bean:write name="rsv210Form" property="rsv210PmToMin" format="00" />
                  </span>
                </a>
              </span>
              &nbsp;&nbsp;
              <span>
                <a href="#!" onclick="setAllTime();">
                  <gsmsg:write key="cmn.allday" />
                  <span class="tooltips">
                    <bean:write name="rsv210Form" property="rsv210AllDayFrHour" format="00" />
                    :
                    <bean:write name="rsv210Form" property="rsv210AllDayFrMin" format="00" />
                    ～
                    <bean:write name="rsv210Form" property="rsv210AllDayToHour" format="00" />
                    :
                    <bean:write name="rsv210Form" property="rsv210AllDayToMin" format="00" />
                  </span>
                </a>
              </span>
            </div>
          </td>
        </tr>
        <html:hidden styleId="rsv210AmFrHour" property="rsv210AmFrHour" />
        <html:hidden styleId="rsv210AmFrMin" property="rsv210AmFrMin" />
        <html:hidden styleId="rsv210AmToHour" property="rsv210AmToHour" />
        <html:hidden styleId="rsv210AmToMin" property="rsv210AmToMin" />
        <html:hidden styleId="rsv210PmFrHour" property="rsv210PmFrHour" />
        <html:hidden styleId="rsv210PmFrMin" property="rsv210PmFrMin" />
        <html:hidden styleId="rsv210PmToHour" property="rsv210PmToHour" />
        <html:hidden styleId="rsv210PmToMin" property="rsv210PmToMin" />
        <html:hidden styleId="rsv210AllDayFrHour" property="rsv210AllDayFrHour" />
        <html:hidden styleId="rsv210AllDayFrMin" property="rsv210AllDayFrMin" />
        <html:hidden styleId="rsv210AllDayToHour" property="rsv210AllDayToHour" />
        <html:hidden styleId="rsv210AllDayToMin" property="rsv210AllDayToMin" />
        </td>
        </tr>
        <tr>
          <th class="no_w w20">
            <span>
              <gsmsg:write key="cmn.content" />
            </span>
          </th>
          <td class="txt_l w80">
            <!-- textareaの先頭行に入力した改行を反映させるため、開始タグの直後には必ず改行をいれること。 -->
            <textarea name="rsv210Naiyo" style="width: 489px;" rows="6" onkeyup="showLengthStr(value, <%=maxLengthNaiyo%>, 'inputlength');" id="inputstr"><bean:write name="rsv210Form" property="rsv210Naiyo" /></textarea>
            <br>
            <span class="formCounter">
              <gsmsg:write key="cmn.current.characters" />:
            </span>
            <span id="inputlength" class="formCounter">0</span>
            <span class="formCounter_max">/<%=maxLengthNaiyo%><gsmsg:write key="cmn.character" />
            </span>
          </td>
        </tr>
        <tr>
          <th class="no_w w20">
            <span>
              <gsmsg:write key="cmn.edit.permissions" />
            </span>
          </th>
          <td class="txt_l w80">
            <span>
              <gsmsg:write key="cmn.comments" />
              <gsmsg:write key="reserve.89" />
              <br> ※
              <gsmsg:write key="reserve.90" />
              <gsmsg:write key="reserve.91" />
            </span>
            <div class="display_flex mt10">
              <span class="verAlignMid">
                <html:radio styleId="lvl1" name="rsv210Form" property="rsv210RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_NONE)%>" />
                <label for="lvl1">
                  <gsmsg:write key="cmn.nolimit" />
                </label>
              </span>
              <span class="ml10 verAlignMid">
                <html:radio styleId="lvl2" name="rsv210Form" property="rsv210RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_PER_AND_ADU)%>" />
                <label for="lvl2">
                  <gsmsg:write key="cmn.only.principal.or.registant" />
                </label>
              </span>
              <span class="ml10 verAlignMid">
                <html:radio styleId="lvl3" name="rsv210Form" property="rsv210RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_GRP_AND_ADU)%>" />
                <label for="lvl3">
                  <gsmsg:write key="cmn.only.affiliation.group.membership" />
                </label>
              </span>
            </div>
          </td>
        </tr>

        <tr>
          <th class="no_w w20">
            <span>
              <gsmsg:write key="cmn.public.kbn" />
            </span>
          </th>
          <td class="txt_l w80">
            <div class="verAlignMid">
              <html:radio styleId="rsv210RsyPublic1" name="rsv210Form" property="rsv210RsyPublic" styleClass="js_public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_ALL)%>" />
              <span class="mr10">
                <label for="rsv210RsyPublic1">
                  <gsmsg:write key="cmn.public" />
                </label>
              </span>
            </div><!-- 
         --><div class="verAlignMid">
              <html:radio styleId="rsv210RsyPublic2" name="rsv210Form" property="rsv210RsyPublic" styleClass="js_public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_PLANS)%>" />
              <span class="mr10">
                <label for="rsv210RsyPublic2">
                  <gsmsg:write key="reserve.175" />
                </label>
              </span>
            </div><!-- 
         --><div class="verAlignMid">
              <html:radio styleId="rsv210RsyPublic5" name="rsv210Form" property="rsv210RsyPublic" styleClass="js_public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_TITLE)%>" />
              <span class="mr10">
                <label for="rsv210RsyPublic5">
                  <gsmsg:write key="reserve.189" />
                 </label>
              </span>
            </div><!-- 
         --><div class="verAlignMid">
              <html:radio styleId="rsv210RsyPublic3" name="rsv210Form" property="rsv210RsyPublic" styleClass="js_public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_GROUP)%>" />
              <span class="mr10">
                <label for="rsv210RsyPublic3">
                  <gsmsg:write key="reserve.176" />
                </label>
              </span>
            </div><!-- 
         --><div class="verAlignMid">
              <html:radio styleId="rsv210RsyPublic4" name="rsv210Form" property="rsv210RsyPublic" styleClass="js_public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_USRGRP)%>" />
              <span>
                <label for="rsv210RsyPublic4">
                  <gsmsg:write key="reserve.187" />
                 </label>
              </span>
            </div>
          </td>
        </tr>
        <tr class="js_selectUsrArea">
          <th class="no_w w20">
            <span>
              <gsmsg:write key="reserve.190" />
            </span>
          </th>
          <td>
            <ui:usrgrpselector name="rsv210Form" property="rsv210PubUsrGrpUI" styleClass="hp215" />
          </td>
        </tr>


        <logic:equal name="rsv210Form" property="schedulePluginKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_USE)%>">

          <tr>
            <th class="no_w w20">
              <span>
                <gsmsg:write key="schedule.3" />
              </span>
            </th>
            <td class="txt_l w80">
              <div class="verAlignMid">
                <html:radio property="rsv210SchKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSV_SCHKBN_USER)%>" styleId="rsvSchKbn0" onclick="rsvSchChange();" />
                <label for="rsvSchKbn0">
                  <span>
                    <gsmsg:write key="cmn.user" />
                  </span>
                </label>
                <html:radio styleClass="ml10" property="rsv210SchKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSV_SCHKBN_GROUP)%>" styleId="rsvSchKbn1" onclick="rsvSchChange();" />
                <label for="rsvSchKbn1">
                  <span>
                    <gsmsg:write key="cmn.group" />
                  </span>
                </label>
              </div>

              <span id="rsvSchGroup">

                <div>
                  [<gsmsg:write key="reserve.167" />]
                </div>

                <html:select property="rsv210SchGroupSid">
                  <logic:notEmpty name="rsv210Form" property="rsv210SchGroupLabel" scope="request">

                    <logic:iterate id="exSchGpBean" name="rsv210Form" property="rsv210SchGroupLabel" scope="request">
                      <%
                      boolean schGpDisabled = false;
                      %>
                      <logic:equal name="exSchGpBean" property="viewKbn" value="false">
                        <%
                        schGpDisabled = true;
                        %>
                      </logic:equal>
                      <bean:define id="gpValue" name="exSchGpBean" property="value" type="java.lang.String" />
                      <logic:equal name="exSchGpBean" property="styleClass" value="0">
                        <html:option value="<%=gpValue%>" disabled="<%=schGpDisabled%>">
                          <bean:write name="exSchGpBean" property="label" />
                        </html:option>
                      </logic:equal>
                      <logic:notEqual name="exSchGpBean" property="styleClass" value="0">
                        <html:option value="<%=gpValue%>" disabled="<%=schGpDisabled%>">
                          <bean:write name="exSchGpBean" property="label" />
                        </html:option>
                      </logic:notEqual>

                    </logic:iterate>
                  </logic:notEmpty>
                </html:select>
                <button type="button" onclick="openGroupWindow_Disabled(this.form.rsv210SchGroupSid, 'rsv210SchGroupSid', '0', '', 1, '', 'rsvSchNotAccessGroup', 1)" class="iconBtn-border" value="&nbsp;&nbsp;" id="rsvSchGrpBtn1">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
                </button>

              </span>
              <div class="w100" id="rsvSchUser">
                <div>
                  [<gsmsg:write key="reserve.166" />]
                </div>

                <ui:usrgrpselector name="rsv210Form" property="rsv210ScheduleUserUI" styleClass="hp215" />
              </div>
            </td>
          </tr>
        </logic:equal>

        <tr>
          <th class="no_w w20">
            <span>
              <gsmsg:write key="schedule.18" />
            </span>
          </th>
          <td class="txt_l w80">
            <span>
              ※
              <gsmsg:write key="schedule.35" />
            </span>
            <button type="button" value="<gsmsg:write key="schedule.17" />" class="baseBtn" onClick="openScheduleReserveWindowForReserve(<%= String.valueOf(Rsv310Form.POP_DSP_MODE_RSV210) %>);">
              <gsmsg:write key="schedule.17" />
            </button>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="submit" value="<gsmsg:write key="cmn.ok" />" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_menu');">
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