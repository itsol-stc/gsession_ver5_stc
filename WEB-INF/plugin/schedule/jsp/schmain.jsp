<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib tagdir="/WEB-INF/tags/schedule/" prefix="schedule" %>
<!DOCTYPE html>

<%-- 定数値 --%>
<%
  String project           = jp.groupsession.v2.cmn.GSConstCommon.PLUGIN_ID_PROJECT;
  String nippou            = jp.groupsession.v2.cmn.GSConstCommon.PLUGIN_ID_NIPPOU;
%>
<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>
<html:html>
<head>
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.schmain.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>
<html:form action="/schedule/schmain" styleClass="js_schForm">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="dspMod" value="4">
<input type="hidden" name="selectDate" value="">
<input type="hidden" name="selectUser" value="">
<input type="hidden" name="selectKbn" value="">
<html:hidden property="schWeekDate" />
<html:hidden property="schSelectDate" />
<html:hidden property="schSelectUsrSid" />
<html:hidden property="schSelectUsrKbn" />
<html:hidden property="schSelectSchSid" />
<html:hidden property="changeDateFlg"/>
<html:hidden property="ptlSid"/>

<div id="tooltips_sch" class="js_easyDispArea">
  <bean:define id="schWeekMdl" name="schmainForm" property="schWeekMdl"/>
  <table class="table-top table_col-even w100 mb0">
    <tr>
      <th class="txt_l table_title-color" colspan="7">
        <img class="mainPlugin_icon" src="../schedule/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="schedule.108" />">
        <a href="#!" onclick="return moveScheduleFromMain('week');">
          <gsmsg:write key="schedule.108" />
        </a>
        <span class="mainSchHeader_contents">
          <button class="baseBtn" type="button" value="<gsmsg:write key="schedule.19" />" onClick="return moveScheduleFromMain('kojin_week');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week_kojin.png" alt="<gsmsg:write key="schedule.19" />">
            <gsmsg:write key="schedule.19" />
          </button>
          <button class="baseBtn " type="button" value="<gsmsg:write key="cmn.between.mon" />" onClick="return moveScheduleFromMain('month');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.between.mon" />">
            <gsmsg:write key="cmn.between.mon" />
          </button>
          <span class="verAlignMid mainSchHeader_move">
            <button class="webIconBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="updateSchedule2('schw_move_lw');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_2_l.png">
              <i class="icon-paging_week_left mainSch_moveIcon"></i>
            </button>
            <button class="webIconBtn mainSch_button-move" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="updateSchedule2('schw_move_ld');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left mainSch_moveIcon"></i>
            </button>
            <button class="baseBtn classic-display" type="button" value="<gsmsg:write key="cmn.today" />" onClick="updateSchedule2('schw_today');">
              <gsmsg:write key="cmn.today" />
            </button>
            <a class="fw_b todayBtn original-display" onClick="updateSchedule2('schw_today');">
              <gsmsg:write key="cmn.today" />
            </a>
            <button class="webIconBtn mainSch_button-move" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="updateSchedule2('schw_move_rd');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right mainSch_moveIcon"></i>
            </button>
            <button class="webIconBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="updateSchedule2('schw_move_rw');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_2_r.png">
              <i class="icon-paging_week_right mainSch_moveIcon"></i>
            </button>
          </span>
        </span>
      </th>
    </tr>
    <tr>
      <logic:notEmpty name="schWeekMdl" property="schWeekCalendarList" scope="page">
        <logic:iterate id="calBean" name="schWeekMdl" property="schWeekCalendarList" scope="page">
          <bean:define id="tdColor" value="" />
          <bean:define id="fontColorSun" value="" />
          <bean:define id="fontColorSat" value="" />
          <bean:define id="fontColorDef" value="" />
          <% String[] tdColors = new String[] {"bgC_header2", "bgC_select","bgC_calSaturday" ,"bgC_calSunday"}; %>
          <% fontColorSun = "cl_fontSunday"; %>
          <% fontColorSat = "cl_fontSaturday"; %>
          <% fontColorDef = "cl_fontBody"; %>
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
          <% String fontColor = ""; %>
          <logic:equal name="calBean" property="holidayKbn" value="1">
            <% fontColor = fontColorSun;%>
          </logic:equal>
          <logic:notEqual name="calBean" property="holidayKbn" value="1">
            <logic:equal name="calBean" property="weekKbn" value="1">
              <% fontColor = fontColorSun;%>
            </logic:equal>
            <logic:equal name="calBean" property="weekKbn" value="7">
              <% fontColor = fontColorSat;%>
            </logic:equal>
            <logic:notEqual name="calBean" property="weekKbn" value="1">
              <logic:notEqual name="calBean" property="weekKbn" value="7">
                <% fontColor = fontColorDef;%>
              </logic:notEqual>
            </logic:notEqual>
          </logic:notEqual>
          <% String rokuyouSpace = ""; %>
          <logic:notEmpty name="calBean" property="rokuyou">
              <% rokuyouSpace = "pb0";%>
          </logic:notEmpty>
          <td class="w14 no_w txt_c fw_bold lh110 <%= tdColor %> <%= rokuyouSpace %>">
            <div class="w100 display_flex schedule_date">
              <div class="wp85 txt_c">
                <a href="#!" class="schedule_headerDay <%= fontColor %>" onClick="moveScheduleFromMain('day', <bean:write name="calBean" property="dspDate" />);">
	              <span>
	                <bean:define id="strDspDate" name="calBean" property="dspDate" type="java.lang.String"/>
	                <%= Integer.parseInt(strDspDate.substring(4, 6)) %><gsmsg:write key="cmn.month" /><bean:write name="calBean" property="dspDayString" />
	              </span>
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
      </logic:notEmpty>
    </tr>
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
    <logic:notEmpty name="usrWeekBean" property="sch010SchList">
      <tr>
        <logic:iterate id="dayMdl" name="usrWeekBean" property="sch010SchList" indexId="schIdx2">
          <bean:define id="offsetIdx" value="" />
          <% offsetIdx = schIdx2.toString(); %>
          <logic:iterate id="gdayMdl" name="grpWeekBean" property="sch010SchList" offset="<%= offsetIdx %>" length="1">
            <logic:equal name="dayMdl" property="weekKbn" value="1">
              <logic:notEqual name="gdayMdl" property="todayKbn" value="1">
                <td class="hp60 bgC_tableCell_Sunday  txt_l txt_t">
              </logic:notEqual>
              <logic:equal name="gdayMdl" property="todayKbn" value="1">
                <td class="hp60 bgC_tableCell  txt_l txt_t">
              </logic:equal>
            </logic:equal>
            <logic:equal name="dayMdl" property="weekKbn" value="7">
              <logic:notEqual name="gdayMdl" property="todayKbn" value="1">
                <td class="hp60 bgC_tableCell_Saturday  txt_l txt_t">
              </logic:notEqual>
              <logic:equal name="gdayMdl" property="todayKbn" value="1">
                <td class="bgC_select hp60 txt_l txt_t">
              </logic:equal>
            </logic:equal>
            <logic:notEqual name="dayMdl" property="weekKbn" value="1">
              <logic:notEqual name="dayMdl" property="weekKbn" value="7">
                <logic:equal name="gdayMdl" property="todayKbn" value="1">
                  <td class="bgC_tableCell hp60 txt_l txt_t">
                </logic:equal>
                <logic:notEqual name="gdayMdl" property="todayKbn" value="1">
                  <td class="bgC_tableCell hp60 txt_l txt_t">
                </logic:notEqual>
              </logic:notEqual>
            </logic:notEqual>
            <logic:notEmpty name="gdayMdl" property="holidayName">
              <div>
                <!-- 登録ボタン -->
                <span id="lt">
                  <a href="#!" onClick="return addSchedule('schw_add', <bean:write name="gdayMdl" property="schDate" />);">
                    <img class="btn_classicImg-display eventImg" src="../common/images/classic/icon_scadd.png" alt="<gsmsg:write key="cmn.add" />">
                    <img class="btn_originalImg-display eventImg" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                  </a>
                  <span class="js_easyRegister cursor_p">
                    <img class="eventImg wp18 ml5" src="../common/images/original/bubble_pen_icon.png" alt="<gsmsg:write key="cmn.add" />">
                    <div class="display_none js_schDate"><bean:write name="dayMdl" property="schDate" /></div>
                    <div class="display_none js_schUsrSid"><bean:write name="dayMdl" property="usrSid" /></div>
                    <div class="display_none js_schUsrKbn"><bean:write name="dayMdl" property="usrKbn" /></div>
                  </span>
                </span>
                <!-- 祝日 -->
                <span id="rt" class="ml5 flo_r cl_fontWarn fs_10 lh100 mt5"><bean:write name="gdayMdl" property="holidayName" /></span>
              </div>
            </logic:notEmpty>
            <logic:empty name="gdayMdl" property="holidayName">
              <div>
              <!-- 登録ボタン -->
              <span id="lt">
                <a href="#!" class="flo_l" onClick="return addSchedule('schw_add', <bean:write name="gdayMdl" property="schDate" />);">
                  <img class="btn_classicImg-display eventImg" src="../common/images/classic/icon_scadd.png" alt="<gsmsg:write key="cmn.add" />">
                  <img class="btn_originalImg-display eventImg" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                </a>
                <span class="js_easyRegister cursor_p">
                  <img class="eventImg wp18 ml10" src="../common/images/original/bubble_pen_icon.png" alt="<gsmsg:write key="cmn.add" />">
                  <div class="display_none js_schDate"><bean:write name="dayMdl" property="schDate" /></div>
                  <div class="display_none js_schUsrSid"><bean:write name="dayMdl" property="usrSid" /></div>
                  <div class="display_none js_schUsrKbn"><bean:write name="dayMdl" property="usrKbn" /></div>
                </span>
              </div>
            </logic:empty>
            <logic:notEmpty name="gdayMdl" property="schDataList">
              <logic:iterate id="gschMdl" name="gdayMdl" property="schDataList">
                <bean:define id="u_usrsid" name="gdayMdl" property="usrSid" type="java.lang.Integer" />
                <bean:define id="u_schsid" name="gschMdl" property="schSid" type="java.lang.Integer" />
                <bean:define id="u_date" name="gdayMdl" property="schDate"  type="java.lang.String" />
                <logic:empty name="gschMdl" property="valueStr">
                  <a href="#!" onClick="return editSchedule('schw_edit', <bean:write name="gdayMdl" property="schDate" />, <bean:write name="gschMdl" property="schSid" />, <bean:write name="gschMdl" property="userSid" />, <bean:write name="gschMdl" property="userKbn" />);">
                    <span class="tooltips"><bean:write name="gschMdl" property="title" /></span>
                </logic:empty>
                <logic:notEmpty name="gschMdl" property="valueStr">
                  <bean:define id="gscnaiyou" name="gschMdl" property="valueStr" />
                    <%
                      String tmpText = (String)pageContext.getAttribute("gscnaiyou",PageContext.PAGE_SCOPE);
                      String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                     %>
                  <a href="#!" onClick="return editSchedule('schw_edit', <bean:write name="gdayMdl" property="schDate" />, <bean:write name="gschMdl" property="schSid" />, <bean:write name="gschMdl" property="userSid" />, <bean:write name="gschMdl" property="userKbn" />);" >
                    <span class="tooltips"><gsmsg:write key="cmn.content" />:<%= tmpText2 %></span>
                </logic:notEmpty>
                <div class="cal_space">
                  <!--タイトルカラー設定-->
                  <logic:equal name="gschMdl" property="bgColor" value="0">
                    <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gschMdl" property="bgColor" value="1">
                    <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gschMdl" property="bgColor" value="2">
                    <div class="cl_fontSchTitleRed opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gschMdl" property="bgColor" value="3">
                    <div class="cl_fontSchTitleGreen opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gschMdl" property="bgColor" value="4">
                    <div class="cl_fontSchTitleYellow opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gschMdl" property="bgColor" value="5">
                    <div class="cl_fontSchTitleBlack opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gschMdl" property="bgColor" value="6">
                    <div class="cl_fontSchTitleNavy opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gschMdl" property="bgColor" value="7">
                    <div class="cl_fontSchTitleWine opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gschMdl" property="bgColor" value="8">
                    <div class="cl_fontSchTitleCien opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gschMdl" property="bgColor" value="9">
                    <div class="cl_fontSchTitleGray opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gschMdl" property="bgColor" value="10">
                    <div class="cl_fontSchTitleMarine opacity6-hover fs_13">
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
            <logic:notEmpty name="dayMdl" property="schDataList">
              <logic:iterate id="schMdl" name="dayMdl" property="schDataList" indexId="schIdx3">
                <bean:define id="u_usrsid" name="dayMdl" property="usrSid" type="java.lang.Integer" />
                <bean:define id="u_schsid" name="schMdl" property="schSid" type="java.lang.Integer" />
                <bean:define id="u_date" name="dayMdl" property="schDate"  type="java.lang.String" />
                <logic:empty name="schMdl" property="valueStr">
                  <a href="#!" onClick="return editSchedule('schw_edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="schMdl" property="schSid" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="dayMdl" property="usrKbn" />);">
                  <span class="tooltips"><bean:write name="schMdl" property="title" /></span>
                </logic:empty>
                <logic:notEmpty name="schMdl" property="valueStr">
                  <bean:define id="scnaiyou" name="schMdl" property="valueStr" />
                  <%
                     String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                     String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                  %>
                  <a href="#!" onClick="return editSchedule('schw_edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="schMdl" property="schSid" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="dayMdl" property="usrKbn" />);" >
                  <span class="tooltips"><gsmsg:write key="cmn.content" />:<%= tmpText2 %></span>
                </logic:notEmpty>
                <div class="cal_space">
                  <!--タイトルカラー設定-->
                  <logic:equal name="schMdl" property="bgColor" value="0">
                    <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="schMdl" property="bgColor" value="1">
                    <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="schMdl" property="bgColor" value="2">
                    <div class="cl_fontSchTitleRed opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="schMdl" property="bgColor" value="3">
                    <div class="cl_fontSchTitleGreen opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="schMdl" property="bgColor" value="4">
                    <div class="cl_fontSchTitleYellow opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="schMdl" property="bgColor" value="5">
                    <div class="cl_fontSchTitleBlack opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="schMdl" property="bgColor" value="6">
                    <div class="cl_fontSchTitleNavy opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="schMdl" property="bgColor" value="7">
                    <div class="cl_fontSchTitleWine opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="schMdl" property="bgColor" value="8">
                    <div class="cl_fontSchTitleCien opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="schMdl" property="bgColor" value="9">
                    <div class="cl_fontSchTitleGray opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="schMdl" property="bgColor" value="10">
                    <div class="cl_fontSchTitleMarine opacity6-hover fs_13">
                  </logic:equal>
                  <logic:notEmpty name="schMdl" property="time">
                    <div class="cal_content-space">
                      <span class="cal_time no_w"><bean:write name="schMdl" property="time" /></span>
                    </div>
                  </logic:notEmpty>
                  <div class="cal_content">
                    <logic:equal name="schMdl" property="publicIconFlg" value="true">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                    </logic:equal>
                    <bean:write name="schMdl" property="title" />
                  </div>
                </div>
                </div>
                </a>
              </logic:iterate>
            </logic:notEmpty>
          </logic:iterate>
        </logic:iterate>
      </tr>
      <!--期間スケジュール-->
      <logic:notEmpty name="usrWeekBean" property="sch010NoTimeSchList">
        <bean:define id="noTimeList" name="usrWeekBean" property="sch010NoTimeSchList" type="java.util.ArrayList"/>
        <% int rowSize = noTimeList.size(); %>
          <logic:iterate id="periodList" name="usrWeekBean" property="sch010NoTimeSchList" indexId="rowIdx">
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
                        <a href="#!"  onClick="return editSchedule('schw_edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="uPeriodMdl" property="schSid" />, <bean:write name="uPeriodMdl" property="userSid" />, <bean:write name="uPeriodMdl" property="userKbn" />);">
                        <span class="tooltips"><bean:write name="uPeriodMdl" property="title" /></span>
                      </logic:empty>
                      <logic:notEmpty name="uPeriodMdl" property="valueStr">
                        <bean:define id="scnaiyou" name="uPeriodMdl" property="valueStr" />
                        <%
                           String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                           String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                        %>
                        <a href="#!" onClick="return editSchedule('schw_edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="uPeriodMdl" property="schSid" />, <bean:write name="uPeriodMdl" property="userSid" />, <bean:write name="uPeriodMdl" property="userKbn" />);" >
                        <span class="tooltips"><gsmsg:write key="cmn.content" />:<%= tmpText2 %></span>
                      </logic:notEmpty>
                    </logic:empty>
                    <logic:notEmpty name="uPeriodMdl" property="schAppendUrl">
                      <logic:empty name="uPeriodMdl" property="valueStr">
                        <a href="<bean:write name="uPeriodMdl" property="schAppendUrl" />">
                        <% boolean schFilter = true; %>
                        <logic:equal name="uPeriodMdl" property="userKbn" value="<%= project %>">
                          <% schFilter = false; %>
                        </logic:equal>
                        <span class="tooltips"><bean:write name="uPeriodMdl" property="title" filter="<%= schFilter %>" /></span>
                      </logic:empty>
                      <logic:notEmpty name="uPeriodMdl" property="valueStr">
                        <bean:define id="scnaiyou" name="uPeriodMdl" property="valueStr" />
                        <%
                          String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                          String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                        %>
                        <a href="<bean:write name="uPeriodMdl" property="schAppendUrl" />">
                        <span class="tooltips"><gsmsg:write key="cmn.content" />:<%= tmpText2 %></span>
                      </logic:notEmpty>
                    </logic:notEmpty>
                    <!--タイトルカラー設定-->
                    <logic:equal name="uPeriodMdl" property="bgColor" value="0">
                      <div class="cl_fontSchTitleBlue opacity6-hover fs_13 txt_c">
                    </logic:equal>
                    <logic:equal name="uPeriodMdl" property="bgColor" value="1">
                      <div class="cl_fontSchTitleBlue opacity6-hover fs_13 txt_c">
                    </logic:equal>
                    <logic:equal name="uPeriodMdl" property="bgColor" value="2">
                      <div class="cl_fontSchTitleRed opacity6-hover fs_13 txt_c">
                    </logic:equal>
                    <logic:equal name="uPeriodMdl" property="bgColor" value="3">
                      <div class="cl_fontSchTitleGreen opacity6-hover fs_13 txt_c">
                    </logic:equal>
                    <logic:equal name="uPeriodMdl" property="bgColor" value="4">
                      <div class="cl_fontSchTitleYellow opacity6-hover fs_13 txt_c">
                    </logic:equal>
                    <logic:equal name="uPeriodMdl" property="bgColor" value="5">
                      <div class="cl_fontSchTitleBlack opacity6-hover fs_13 txt_c">
                    </logic:equal>
                    <logic:equal name="uPeriodMdl" property="bgColor" value="6">
                      <div class="cl_fontSchTitleNavy opacity6-hover fs_13 txt_c">
                    </logic:equal>
                    <logic:equal name="uPeriodMdl" property="bgColor" value="7">
                      <div class="cl_fontSchTitleWine opacity6-hover fs_13 txt_c">
                    </logic:equal>
                    <logic:equal name="uPeriodMdl" property="bgColor" value="8">
                      <div class="cl_fontSchTitleCien opacity6-hover fs_13 txt_c">
                    </logic:equal>
                    <logic:equal name="uPeriodMdl" property="bgColor" value="9">
                      <div class="cl_fontSchTitleGray opacity6-hover fs_13 txt_c">
                    </logic:equal>
                    <logic:equal name="uPeriodMdl" property="bgColor" value="10">
                      <div class="cl_fontSchTitleMarine opacity6-hover fs_13 txt_c">
                    </logic:equal>
                    <% boolean schFilter = true; %>
                    <div class="cal_todoSpace">
                      <logic:equal name="dayMdl" property="usrKbn" value="0">
                        <logic:equal name="uPeriodMdl" property="userKbn" value="1">
                          <span class="cal_label-g classic-display">G</span>
                          <span class="cal_label-g original-display"></span>
                        </logic:equal>
                        <logic:equal name="uPeriodMdl" property="userKbn" value="<%= project %>">
                          <span class="cal_label-todo">TODO</span>
                          <% schFilter = false; %>
                        </logic:equal>
                        <logic:equal name="uPeriodMdl" property="userKbn" value="<%= nippou %>">
                          <span class="cal_label-action">アクション</span>
                        </logic:equal>
                      </logic:equal>
                      <logic:notEmpty name="uPeriodMdl" property="time">
                        <span class="fs_11"><bean:write name="uPeriodMdl" property="time" /><br></span>
                      </logic:notEmpty>
                      <logic:equal name="uPeriodMdl" property="publicIconFlg" value="true">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                      </logic:equal>
                      <bean:write name="uPeriodMdl" property="title" filter="<%= schFilter %>"/>
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
    </logic:notEmpty>
  </table>
</div>
<div class="txt_l">
  <bean:define id="id" name="schmainForm" property="usrKbnId" />
  <%  String classId = String.valueOf(id);%>
  <schedule:schEasyRegister name="schmainForm" classId="<%=classId%>" />
</div>
</html:form>
</body>
</html:html>