<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib tagdir="/WEB-INF/tags/schedule/" prefix="schedule" %>
<!DOCTYPE html>

<html:html>
<head>
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.schmain.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<bean:define id="grpSid" name="schptl010Form" property="schDspGrpSid" type="java.lang.Integer" />
<% String schFormId = "schptl010Form" + String.valueOf(grpSid.intValue()); %>
</head>
<body>
<html:form action="/schedule/schptl010" styleId="<%= schFormId %>" styleClass="js_schForm">
<logic:equal name="schptl010Form" property="schPtlDspFlg" value="0">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="dspMod" value="4">
<input type="hidden" name="selectDate" value="">
<input type="hidden" name="selectUser" value="">
<input type="hidden" name="selectKbn" value="">
<html:hidden property="schWeekDate" />
<html:hidden property="schSelectDate" />
<html:hidden property="schSelectUsrSid" />
<html:hidden property="schSelectSchSid" />
<html:hidden property="schDspGrpSid" />
<html:hidden property="schPtl010ItemId" />
<html:hidden property="ptlPortalSid" />

<div id="tooltips_sch<bean:write name="schptl010Form" property="schDspGrpSid" />" class="js_easyDispArea">
  <bean:define id="schWeekMdl" name="schptl010Form" property="schWeekMdl"/>
  <table class="table-top table_col-even w100 mb0">
    <tr>
      <th class="table_title-color txt_l" colspan=7>
        <img src="../schedule/images/classic/menu_icon_single.gif" class="mainPlugin_icon" alt="<gsmsg:write key="schedule.108" />">
        <a href="#!"  onClick="return moveWeekScheduleFromPtl('week', '<%= schFormId %>');">
          <bean:write name="schptl010Form" property="schPtl010GrpName" />
        </a>
        <span class="mainSchHeader_contents">
          <button class="baseBtn" type="button" value="<gsmsg:write key="cmn.between.mon" />" onClick="return moveMonthScheduleFromPtl('month', '<%= schFormId %>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.between.mon" />">
            <gsmsg:write key="cmn.between.mon" />
          </button>
          <span class="verAlignMid mainSchHeader_move">
            <button class="webIconBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="updateSchedulePtl('schw_move_lw', '<%= schFormId %>');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_2_l.png">
              <i class="icon-paging_week_left ptl_moveIcon"></i>
            </button>
            <button class="webIconBtn mainSch_button-move" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="updateSchedulePtl('schw_move_ld', '<%= schFormId %>');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left ptl_moveIcon"></i>
            </button>
            <button class="baseBtn classic-display" type="button" value="<gsmsg:write key="cmn.today" />" onClick="updateSchedulePtl('schw_today', '<%= schFormId %>');">
              <gsmsg:write key="cmn.today" />
            </button>
            <a class="fw_b todayBtn original-display" onClick="updateSchedulePtl('schw_today', '<%= schFormId %>');">
              <gsmsg:write key="cmn.today" />
            </a>
            <button class="webIconBtn mainSch_button-move" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="updateSchedulePtl('schw_move_rd', '<%= schFormId %>');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right ptl_moveIcon"></i>
            </button>
            <button class="webIconBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="updateSchedulePtl('schw_move_rw', '<%= schFormId %>');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_2_r.png">
              <i class="icon-paging_week_right ptl_moveIcon"></i>
            </button>
          </span>
        </span>
      </th>
    </tr>
    <logic:notEmpty name="schWeekMdl" property="schWeekCalendarList" scope="page">
      <tr>
        <logic:iterate id="calBean" name="schWeekMdl" property="schWeekCalendarList" scope="page">
          <bean:define id="tdColor" value="" />
          <bean:define id="fontColorSun" value="" />
          <bean:define id="fontColorSat" value="" />
          <bean:define id="fontColorDef" value="" />
          <% String[] tdColors = new String[] {"bgC_header2", "bgC_select","bgC_calSaturday" ,"bgC_calSunday"}; %>
          <% fontColorSun = "schedule_headerDay cl_fontSunday"; %>
          <% fontColorSat = "schedule_headerDay cl_fontSaturday"; %>
          <% fontColorDef = "schedule_headerDay cl_fontBody"; %>
          <logic:equal name="calBean" property="todayKbn" value="1">
            <% tdColor = tdColors[1]; %>
          </logic:equal>
          <logic:notEqual name="calBean" property="todayKbn" value="1">
           <logic:equal name="calBean" property="holidayKbn" value="1">
             <% tdColor = tdColors[0]; %>
           </logic:equal>
           <logic:notEqual name="calBean" property="holidayKbn" value="1">
           <logic:equal name="calBean" property="weekKbn" value="1">
           <% tdColor = tdColors[3]; %>
           </logic:equal>
           <logic:equal name="calBean" property="weekKbn" value="7">
             <% tdColor = tdColors[2]; %>
           </logic:equal>
           <logic:notEqual name="calBean" property="weekKbn" value="1">
             <logic:notEqual name="calBean" property="weekKbn" value="7">
           <% tdColor = tdColors[0]; %>
             </logic:notEqual>
           </logic:notEqual>
            </logic:notEqual>
          </logic:notEqual>
          <% String rokuyouSpace = ""; %>
          <logic:notEmpty name="calBean" property="rokuyou">
              <% rokuyouSpace = "pb0";%>
          </logic:notEmpty>
          <td class="w14 txt_c no_w fw_bold lh110 <%= tdColor %> <%= rokuyouSpace %>">
            <div class="w100 display_flex schedule_date">
              <div class="wp85 txt_c">
	            <a href="#!" onClick="return moveDailyScheduleFromPtl('day', <bean:write name="calBean" property="dspDate" />, '<%= schFormId %>');">
	              <logic:equal name="calBean" property="holidayKbn" value="1">
	                <span class="<%= fontColorSun %>">
	              </logic:equal>
	              <logic:notEqual name="calBean" property="holidayKbn" value="1">
	                <logic:equal name="calBean" property="weekKbn" value="1">
	                  <span class="<%= fontColorSun %>">
	                </logic:equal>
	                <logic:equal name="calBean" property="weekKbn" value="7">
	                  <span class="<%= fontColorSat %>">
	                </logic:equal>
	                <logic:notEqual name="calBean" property="weekKbn" value="1">
	                  <logic:notEqual name="calBean" property="weekKbn" value="7">
	                    <span class="<%= fontColorDef %>">
	                  </logic:notEqual>
	                </logic:notEqual>
	              </logic:notEqual>
	            <bean:define id="strDspDate" name="calBean" property="dspDate" type="java.lang.String"/>
	              <%= Integer.parseInt(strDspDate.substring(4, 6)) %><gsmsg:write key="cmn.month" /><bean:write name="calBean" property="dspDayString" /></span>
	            </a>
              </div>
              <div class="rokuyou ml3 mt3 fw_n">
	            <% String rkyName = ""; %>
	            <bean:define id="rkyKbn" name="calBean" property="rokuyou" />
	            <%
	            if (rkyKbn.equals(String.valueOf(jp.groupsession.v2.cmn.GSConst.RKY_KBN_SENSHOU))) {
	                rkyName = jp.groupsession.v2.cmn.GSConst.ROKUYOU_SENSHOU;
	            } else if (rkyKbn.equals(String.valueOf(jp.groupsession.v2.cmn.GSConst.RKY_KBN_TOMOBIKI))) {
	                rkyName = jp.groupsession.v2.cmn.GSConst.ROKUYOU_TOMOBIKI;
	            } else if (rkyKbn.equals(String.valueOf(jp.groupsession.v2.cmn.GSConst.RKY_KBN_SENBU))) {
	                rkyName = jp.groupsession.v2.cmn.GSConst.ROKUYOU_SENBU;
	            } else if (rkyKbn.equals(String.valueOf(jp.groupsession.v2.cmn.GSConst.RKY_KBN_BUTSUMETSU))) {
	                rkyName = jp.groupsession.v2.cmn.GSConst.ROKUYOU_BUTSUMETSU;;
	            } else if (rkyKbn.equals(String.valueOf(jp.groupsession.v2.cmn.GSConst.RKY_KBN_TAIAN))) {
	                rkyName = jp.groupsession.v2.cmn.GSConst.ROKUYOU_TAIAN;
	            } else if (rkyKbn.equals(String.valueOf(jp.groupsession.v2.cmn.GSConst.RKY_KBN_SHAKKOU))) {
	                rkyName = jp.groupsession.v2.cmn.GSConst.ROKUYOU_SHAKKOU;
	            }
	            %>
	            <%= rkyName %>
	          </div>
	        </div>
          </td>
        </logic:iterate>
      </tr>
    </logic:notEmpty>

    <logic:iterate id="weekBean" name="schWeekMdl" property="schWeekTopList" scope="page" indexId="schIdx1" offset="0">
      <logic:equal name="schIdx1" value="0">
        <!-- スケジュール情報(グループ予定) -->
        <bean:define id="grpWeekBean" name="weekBean"/>
      </logic:equal>
      <logic:equal name="schIdx1" value="1">
        <!-- スケジュール情報(個人予定) -->
        <bean:define id="usrWeekBean" name="weekBean"/>
      </logic:equal>
    </logic:iterate>

    <logic:notEmpty name="grpWeekBean" property="sch010SchList">
      <tr>
        <logic:iterate id="dayMdl" name="grpWeekBean" property="sch010SchList" indexId="schIdx2">
          <bean:define id="offsetIdx" value="" />
          <% offsetIdx = schIdx2.toString(); %>
          <logic:iterate id="gdayMdl" name="grpWeekBean" property="sch010SchList" offset="<%= offsetIdx %>" length="1">

            <logic:equal name="gdayMdl" property="weekKbn" value="1">
              <logic:notEqual name="gdayMdl" property="todayKbn" value="1">
                <td class="hp60 bgC_tableCell_Sunday  txt_l txt_t">
              </logic:notEqual>
              <logic:equal name="gdayMdl" property="todayKbn" value="1">
                <td class="hp60 bgC_tableCell  txt_l txt_t">
              </logic:equal>
            </logic:equal>

            <logic:equal name="gdayMdl" property="weekKbn" value="7">
              <logic:notEqual name="gdayMdl" property="todayKbn" value="1">
                <td class="hp60 bgC_tableCell_Saturday  txt_l txt_t">
              </logic:notEqual>
              <logic:equal name="gdayMdl" property="todayKbn" value="1">
                <td class="hp60 bgC_tableCell  txt_l txt_t">
              </logic:equal>
            </logic:equal>

            <logic:notEqual name="gdayMdl" property="weekKbn" value="1">
              <logic:notEqual name="gdayMdl" property="weekKbn" value="7">
                <logic:notEqual name="gdayMdl" property="todayKbn" value="1">
                  <td class="bgC_tableCell hp60 txt_l txt_t">
                </logic:notEqual>
                <logic:equal name="gdayMdl" property="todayKbn" value="1">
                  <td class="hp60 bgC_tableCell  txt_l txt_t">
                </logic:equal>
              </logic:notEqual>
            </logic:notEqual>

            <logic:equal name="schptl010Form" property="schRegistFlg" value="true">
              <logic:notEmpty name="gdayMdl" property="holidayName">
                <div>
                  <!-- スケジュール追加ボタン -->
                  <span id="lt">
                    <a href="#!" onClick="return addSchedulePtl('schw_add', <bean:write name="gdayMdl" property="schDate" />, '<%= schFormId %>');">
                      <img class="btn_classicImg-display eventImg" src="../common/images/classic/icon_scadd.png" alt="<gsmsg:write key="cmn.add" />">
                      <img class="btn_originalImg-display eventImg" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                    </a>
                    <span class="js_easyRegister cursor_p">
                      <img class="eventImg wp18 ml5" src="../common/images/original/bubble_pen_icon.png" alt="<gsmsg:write key="cmn.add" />">
                      <div class="display_none js_schDate"><bean:write name="gdayMdl" property="schDate" /></div>
                      <div class="display_none js_schUsrSid"><bean:write name="gdayMdl" property="usrSid" /></div>
                      <div class="display_none js_schUsrKbn"><bean:write name="gdayMdl" property="usrKbn" /></div>
                    </span>
                  </span>
                  <span id="rt" class="ml5 flo_r cl_fontWarn fs_10 lh100 mt5"><bean:write name="gdayMdl" property="holidayName" /></span>
                </div>
              </logic:notEmpty>
              <logic:empty name="gdayMdl" property="holidayName">
                <div>
                  <!-- スケジュール追加ボタン -->
                  <span id="lt">
                    <a href="#!" class="flo_l" onClick="return addSchedulePtl('schw_add', <bean:write name="gdayMdl" property="schDate" />, '<%= schFormId %>');">
                      <img class="btn_classicImg-display eventImg" src="../common/images/classic/icon_scadd.png" alt="<gsmsg:write key="cmn.add" />">
                      <img class="btn_originalImg-display eventImg" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                    </a>
                    <span class="js_easyRegister cursor_p">
                      <img class="eventImg wp18 ml10" src="../common/images/original/bubble_pen_icon.png" alt="<gsmsg:write key="cmn.add" />">
                      <div class="display_none js_schDate"><bean:write name="gdayMdl" property="schDate" /></div>
                      <div class="display_none js_schUsrSid"><bean:write name="gdayMdl" property="usrSid" /></div>
                      <div class="display_none js_schUsrKbn"><bean:write name="gdayMdl" property="usrKbn" /></div>
                    </span>
                  </span>
                </div>
              </logic:empty>
            </logic:equal>

            <logic:equal name="schptl010Form" property="schRegistFlg" value="false">
              <logic:notEmpty name="gdayMdl" property="holidayName">
                  <span id="rt" class="cl_fontWarn fs_10 flo_r txt_l lh100 mt5"><bean:write name="gdayMdl" property="holidayName" /></span>
              </logic:notEmpty>
            </logic:equal>

            <logic:notEmpty name="gdayMdl" property="schDataList">
              <logic:iterate id="gschMdl" name="gdayMdl" property="schDataList">
                <bean:define id="u_usrsid" name="gdayMdl" property="usrSid" type="java.lang.Integer" />
                <bean:define id="u_schsid" name="gschMdl" property="schSid" type="java.lang.Integer" />
                <bean:define id="u_date" name="gdayMdl" property="schDate"  type="java.lang.String" />

                <logic:empty name="gschMdl" property="valueStr">
                  <a href="#!" onClick="return editSchedulePtl('schw_edit', <bean:write name="gdayMdl" property="schDate" />, <bean:write name="gschMdl" property="schSid" />, '<%= schFormId %>');">
                    <span class="tooltips"><bean:write name="gschMdl" property="title" /></span>
                </logic:empty>

                <logic:notEmpty name="gschMdl" property="valueStr">
                  <bean:define id="gscnaiyou" name="gschMdl" property="valueStr" />
                  <%
                    String tmpText = (String)pageContext.getAttribute("gscnaiyou",PageContext.PAGE_SCOPE);
                    String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                  %>
                  <a href="#!" onClick="return editSchedulePtl('schw_edit', <bean:write name="gdayMdl" property="schDate" />, <bean:write name="gschMdl" property="schSid" />, '<%= schFormId %>');" title="">
                    <span class="tooltips"><gsmsg:write key="cmn.content" />:<%= tmpText2 %></span>
                </logic:notEmpty>
                <div class="cal_space">

                <!--タイトルカラー設定-->
                <logic:equal name="gschMdl" property="bgColor" value="0">
                  <div class="cl_fontSchTitleBlue opacity6-hover fs_13 pl0 w100">
                </logic:equal>
                <logic:equal name="gschMdl" property="bgColor" value="1">
                  <div class="cl_fontSchTitleBlue opacity6-hover fs_13 pl0 w100">
                </logic:equal>
                <logic:equal name="gschMdl" property="bgColor" value="2">
                  <div class="cl_fontSchTitleRed opacity6-hover fs_13 pl0 w100">
                </logic:equal>
                <logic:equal name="gschMdl" property="bgColor" value="3">
                  <div class="cl_fontSchTitleGreen opacity6-hover fs_13 pl0 w100">
                </logic:equal>
                <logic:equal name="gschMdl" property="bgColor" value="4">
                  <div class="cl_fontSchTitleYellow opacity6-hover fs_13 pl0 w100">
                </logic:equal>
                <logic:equal name="gschMdl" property="bgColor" value="5">
                  <div class="cl_fontSchTitleBlack opacity6-hover fs_13 pl0 w100">
                </logic:equal>
                <logic:equal name="gschMdl" property="bgColor" value="6">
                  <div class="cl_fontSchTitleNavy opacity6-hover fs_13 pl0 w100">
                </logic:equal>
                <logic:equal name="gschMdl" property="bgColor" value="7">
                  <div class="cl_fontSchTitleWine opacity6-hover fs_13 pl0 w100">
                </logic:equal>
                <logic:equal name="gschMdl" property="bgColor" value="8">
                  <div class="cl_fontSchTitleCien opacity6-hover fs_13 pl0 w100">
                </logic:equal>
                <logic:equal name="gschMdl" property="bgColor" value="9">
                  <div class="cl_fontSchTitleGray opacity6-hover fs_13 pl0 w100">
                </logic:equal>
                <logic:equal name="gschMdl" property="bgColor" value="10">
                  <div class="cl_fontSchTitleMarine opacity6-hover fs_13 pl0 w100">
                </logic:equal>

                <div class="cal_content-space">
                  <logic:notEmpty name="gschMdl" property="time">
                    <span class="cal_time no_w">
                      <span class="cal_label-g classic-display">G</span>
                      <span class="cal_label-g original-display"></span>
                      <bean:write name="gschMdl" property="time" />
                    </span>
                  </logic:notEmpty>
                </div>
                <div class="cal_content">
                  <logic:equal name="gschMdl" property="publicIconFlg" value="true">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                  </logic:equal>
                  <bean:write name="gschMdl" property="title" />
                </div>
                </div>
                </div>
                </a>
              </logic:iterate>
            </logic:notEmpty>
            </td>
          </logic:iterate>
        </logic:iterate>
      </tr>
    </logic:notEmpty>

    <!--時間指定無しスケジュール-->
    <logic:notEmpty name="grpWeekBean" property="sch010NoTimeSchList">
      <bean:define id="noTimeList" name="grpWeekBean" property="sch010NoTimeSchList" type="java.util.ArrayList"/>
      <% int rowSize = noTimeList.size(); %>
      <logic:iterate id="periodList" name="grpWeekBean" property="sch010NoTimeSchList" indexId="rowIdx">
        <logic:notEmpty name="periodList">
          <tr>
            <bean:define id="prList" name="periodList" type="java.util.ArrayList"/>
            <% int size = prList.size(); %>
            <logic:iterate id="uPeriodMdl" name="periodList" indexId="pIdx">
              <logic:notEmpty name="uPeriodMdl" property="periodMdl">
                <bean:define id="pMdl" name="uPeriodMdl" property="periodMdl"/>
                <td class="cal_periodCell" colspan="<bean:write name="pMdl" property="schPeriodCnt" />">
                  <bean:define id="p_schsid" name="uPeriodMdl" property="schSid" type="java.lang.Integer" />
                  <bean:define id="p_public" name="uPeriodMdl" property="public"  type="java.lang.Integer" />
                  <bean:define id="p_kjnEdKbn" name="uPeriodMdl" property="kjnEdKbn"  type="java.lang.Integer" />
                  <bean:define id="p_grpEdKbn" name="uPeriodMdl" property="grpEdKbn"  type="java.lang.Integer" />
                  <%
                    int publicType = ((Integer)pageContext.getAttribute("p_public",PageContext.PAGE_SCOPE));
                    int kjnEdKbn = ((Integer)pageContext.getAttribute("p_kjnEdKbn",PageContext.PAGE_SCOPE));
                    int grpEdKbn = ((Integer)pageContext.getAttribute("p_grpEdKbn",PageContext.PAGE_SCOPE));
                  %>
                  <!--公開-->
                  <logic:empty name="uPeriodMdl" property="schAppendUrl">
                    <logic:empty name="uPeriodMdl" property="valueStr">
                      <a href="#!" onClick="return editSchedulePtl('schw_edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="uPeriodMdl" property="schSid" />, '<%= schFormId %>');">
                        <span class="tooltips">
                          <bean:write name="uPeriodMdl" property="title" />
                        </span>
                    </logic:empty>
                    <logic:notEmpty name="uPeriodMdl" property="valueStr">
                      <bean:define id="scnaiyou" name="uPeriodMdl" property="valueStr" />
                      <%
                        String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                        String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                      %>
                      <a href="#!" onClick="return editSchedulePtl('schw_edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="uPeriodMdl" property="schSid" />, '<%= schFormId %>');">
                        <span class="tooltips">
                          <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                        </span>
                    </logic:notEmpty>
                  </logic:empty>
                  <logic:notEmpty name="uPeriodMdl" property="schAppendUrl">
                    <logic:empty name="uPeriodMdl" property="valueStr">
                      <a href="<bean:write name="uPeriodMdl" property="schAppendUrl" />">
                        <span class="tooltips"><bean:write name="uPeriodMdl" property="title" /></span>
                    </logic:empty>
                    <logic:notEmpty name="uPeriodMdl" property="valueStr">
                      <bean:define id="scnaiyou" name="uPeriodMdl" property="valueStr" />
                      <%
                        String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                        String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                      %>
                      <a href="<bean:write name="uPeriodMdl" property="schAppendUrl" />">
                        <span class="tooltips">
                          <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                        </span>
                    </logic:notEmpty>
                  </logic:notEmpty>
                  <!--タイトルカラー設定-->
                  <logic:equal name="uPeriodMdl" property="bgColor" value="0">
                    <div class="cl_fontSchTitleBlue opacity6-hover">
                  </logic:equal>
                  <logic:equal name="uPeriodMdl" property="bgColor" value="1">
                    <div class="cl_fontSchTitleBlue opacity6-hover">
                  </logic:equal>
                  <logic:equal name="uPeriodMdl" property="bgColor" value="2">
                    <div class="cl_fontSchTitleRed opacity6-hover">
                  </logic:equal>
                  <logic:equal name="uPeriodMdl" property="bgColor" value="3">
                    <div class="cl_fontSchTitleGreen opacity6-hover">
                  </logic:equal>
                  <logic:equal name="uPeriodMdl" property="bgColor" value="4">
                    <div class="cl_fontSchTitleYellow opacity6-hover">
                  </logic:equal>
                  <logic:equal name="uPeriodMdl" property="bgColor" value="5">
                    <div class="cl_fontSchTitleBlack opacity6-hover">
                  </logic:equal>
                  <logic:equal name="uPeriodMdl" property="bgColor" value="6">
                    <div class="cl_fontSchTitleNavy opacity6-hover">
                  </logic:equal>
                  <logic:equal name="uPeriodMdl" property="bgColor" value="7">
                    <div class="cl_fontSchTitleWine opacity6-hover">
                  </logic:equal>
                  <logic:equal name="uPeriodMdl" property="bgColor" value="8">
                    <div class="cl_fontSchTitleCien opacity6-hover">
                  </logic:equal>
                  <logic:equal name="uPeriodMdl" property="bgColor" value="9">
                    <div class="cl_fontSchTitleGray opacity6-hover">
                  </logic:equal>
                  <logic:equal name="uPeriodMdl" property="bgColor" value="10">
                    <div class="cl_fontSchTitleMarine opacity6-hover">
                  </logic:equal>
                  <div class="cal_todoSpace">
                    <span class="cal_label-g classic-display">G</span>
                    <span class="cal_label-g original-display"></span>
                    <logic:notEmpty name="uPeriodMdl" property="time">
                      <bean:write name="uPeriodMdl" property="time" />
                    </logic:notEmpty>
                    <div class="cal_content">
                      <logic:equal name="uPeriodMdl" property="publicIconFlg" value="true">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                      </logic:equal>
                      <bean:write name="uPeriodMdl" property="title" filter="false"/>
                    </div>
                  </div>
                  </div>
                  </a>
                </td>
              </logic:notEmpty>
              <logic:empty name="uPeriodMdl" property="periodMdl">
                <td class="cal_periodCell-less"></td>
              </logic:empty>
            </logic:iterate>
          </tr>
        </logic:notEmpty>
      </logic:iterate>
    </logic:notEmpty>
  </table>
</div>
</logic:equal>
<div class="txt_l">
  <%  String classId = String.valueOf(grpSid); %>
  <schedule:schEasyRegister name="schptl010Form" classId="<%=classId%>" />
</div>
</html:form>

</body>
</html:html>