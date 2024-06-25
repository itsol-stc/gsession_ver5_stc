<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.sch.sch010.Sch010WeekOfModel" %>

<% int editConfOwn = Integer.valueOf(request.getParameter("editConfOwn"));%>
<% int editConfGroup = Integer.valueOf(request.getParameter("editConfGroup"));%>
<% int dspPublic = Integer.valueOf(request.getParameter("dspPublic"));%>
<% int dspBelongGroup = Integer.valueOf(request.getParameter("dspBelongGroup"));%>
<% String project = request.getParameter("project");%>
<% String nippou = request.getParameter("nippou");%>
<% String grpHeight = request.getParameter("grpHeight");%>
<% long schTipCnt = Long.valueOf(request.getParameter("schTipCnt"));%>

<logic:notEmpty name="sch010Form" property="sch010TopList">
  <logic:iterate id="weekBean" name="sch010Form" property="sch010TopList" scope="request" offset="0"/>
  <bean:define id="usrBean" name="weekBean" property="sch010UsrMdl"/>
</logic:notEmpty>
<logic:empty name="sch010Form" property="sch010TopList">
  <bean:define id="usrBean" name="sch010Form" property="sch010UserData" />
</logic:empty>
<bean:define id="sessionUsrSid" name="usrBean" property="usrSid" />

  <logic:iterate id="gpWeekMdl" name="sch010Form" property="sch010BottomList" scope="request" indexId="cnt">
    <bean:define id="ret" value="<%= String.valueOf(cnt.intValue() % 5) %>" />
    <!-- 5行毎にタイトル(氏名,日付) -->
    <logic:notEqual name="cnt" value="0" scope="page">
      <logic:equal name="ret" value="0">
        <tr class="calendar_weekHeader">
          <td class="cal_header w16 fw_b">
            <gsmsg:write key="cmn.name" />
          </td>
          <logic:notEmpty name="sch010Form" property="sch010CalendarList" scope="request">
            <logic:iterate id="calBean" name="sch010Form" property="sch010CalendarList" scope="request">
              <bean:define id="tdColor" value="" />
              <% String[] tdColors = new String[] {"cal_header", "bgC_select","bgC_calSaturday" ,"bgC_calSunday"}; %>
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
              <% String rkyClass = ""; %>
              <bean:define id="rkyKbn" name="calBean" property="rokuyou" />
              <%
                if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_SENSHOU))) {
                    rkyClass = "header_rokuyou-senshou";
                } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_TOMOBIKI))) {
                    rkyClass = "header_rokuyou-tomobiki";
                } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_SENBU))) {
                    rkyClass = "header_rokuyou-senbu";
                } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_BUTSUMETSU))) {
                    rkyClass = "header_rokuyou-butsumetsu";
                } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_TAIAN))) {
                    rkyClass = "header_rokuyou-taian";
                } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_SHAKKOU))) {
                    rkyClass = "header_rokuyou-shakkou";
                }
              %>
              <logic:equal name="calBean" property="holidayKbn" value="1">
                <td class="no_w <%= tdColor %> w12">
                  <div class="<%= rkyClass %>">
                    <a class="schedule_headerDay cl_fontSunday" href="#" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                      <span class="fw_b"><bean:write name="calBean" property="dspDayString" /></span>
                    </a>
                  </div>
                </td>
              </logic:equal>
              <logic:notEqual name="calBean" property="holidayKbn" value="1">
                <logic:equal name="calBean" property="weekKbn" value="1">
                  <td class="no_w <%= tdColor %> w12">
                    <div class="<%= rkyClass %>">
                      <a href="#" class="schedule_headerDay cl_fontSunday" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                        <span class="fw_b"><bean:write name="calBean" property="dspDayString" /></span>
                      </a>
                    </div>
                  </td>
                </logic:equal>
                <logic:equal name="calBean" property="weekKbn" value="7">
                  <td class="no_w <%= tdColor %> w12">
                    <div class="<%= rkyClass %>">
                      <a href="#" class="schedule_headerDay cl_fontSaturday" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                        <span class="fw_b"><bean:write name="calBean" property="dspDayString" /></span>
                      </a>
                    </div>
                  </td>
                </logic:equal>
                <logic:notEqual name="calBean" property="weekKbn" value="1">
                  <logic:notEqual name="calBean" property="weekKbn" value="7">
                    <td class="no_w <%= tdColor %> w12">
                      <div class="<%= rkyClass %>">
                        <a href="#" class="schedule_headerDay cl_fontBody" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                          <span class="fw_b"><bean:write name="calBean" property="dspDayString" /></span>
                        </a>
                      </div>
                    </td>
                  </logic:notEqual>
                </logic:notEqual>
              </logic:notEqual>
            </logic:iterate>
          </logic:notEmpty>
        </tr>
      </logic:equal>
    </logic:notEqual>
    <bean:define id="usrMdl" name="gpWeekMdl" property="sch010UsrMdl"/>
    <tr align="left" valign="top">
      <!-- ユーザ欄 -->
      <bean:define id="usrMdl" name="gpWeekMdl" property="sch010UsrMdl"/>

      <logic:equal name="usrMdl" property="usrKbn" value="1">
      <!-- グループ氏名 -->
        <td class="w16 bgC_tableCell cal_colHeader" rowspan="<bean:write name="gpWeekMdl" property="sch010PeriodRow" />">
        <span class="flo_l">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
        </span>
        <span class="fs_14 flo_l"><bean:write name="usrMdl" property="usrName" /></span><br>
        <span>
          <a href="#" onclick="moveSchedule('month', 'G<bean:write name="usrMdl" property="usrSid" />', <bean:write name="usrMdl" property="usrKbn" />);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
            <gsmsg:write key="cmn.monthly" />
          </a><br>
          <a href="#" onclick="moveSchedule('list', 'G<bean:write name="usrMdl" property="usrSid" />', <bean:write name="usrMdl" property="usrKbn" />);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_list.png" alt="<gsmsg:write key="cmn.list" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
            <span class="fs_13"><gsmsg:write key="cmn.list" /></span>
          </a>
        </span>
        </td>
      </logic:equal>
      

      <logic:notEqual name="usrMdl" property="usrKbn" value="1">
      <!-- ユーザ氏名 -->
        <logic:equal name="sch010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
          <logic:equal name="usrMdl" property="zaisekiKbn" value="1">
            <td class="w16 cal_colHeader-zaiseki" rowspan="<bean:write name="gpWeekMdl" property="sch010PeriodRow" />">
          </logic:equal>
          <logic:equal name="usrMdl" property="zaisekiKbn" value="2">
            <td class="w16 cal_colHeader-huzai" rowspan="<bean:write name="gpWeekMdl" property="sch010PeriodRow" />">
          </logic:equal>
          <logic:equal name="usrMdl" property="zaisekiKbn" value="0">
            <td class="w16 cal_colHeader-sonota" rowspan="<bean:write name="gpWeekMdl" property="sch010PeriodRow" />">
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="sch010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
          <td class="w16 bgC_tableCell" rowspan="<bean:write name="gpWeekMdl" property="sch010PeriodRow" />">
        </logic:notEqual>
      <%-- ユーザ名 --%>
      <span>
        <a href="#!" onClick="openUserInfoWindow(<bean:write name="usrMdl" property="usrSid" />);">
          <logic:equal value="1" name="usrMdl" property="schUkoFlg">
            <span class="mukoUser fs_14"><bean:write name="usrMdl" property="usrName" /></span>
          </logic:equal>
          <logic:notEqual value="1" name="usrMdl" property="schUkoFlg">
            <span class="fs_14"><bean:write name="usrMdl" property="usrName" /></span>
          </logic:notEqual>
        </a>
        <span class="cl_fontSchTitleBlack fs_13"><bean:write name="usrMdl" property="zaisekiMsg" /></span><br>
        <a href="#" onClick="moveSchedule('month', <bean:write name="usrMdl" property="usrSid" />, <bean:write name="usrMdl" property="usrKbn" />);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <gsmsg:write key="cmn.monthly" />
        </a><br>
        <a href="#" onClick="moveSchedule('list', <bean:write name="usrMdl" property="usrSid" />, <bean:write name="usrMdl" property="usrKbn" />);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_list.png" alt="<gsmsg:write key="cmn.list" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
          <gsmsg:write key="cmn.list" />
        </a><br>
        <logic:equal name="sch010Form" property="smailUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
          <logic:equal name="usrMdl" property="smlAble" value="1">
            <a href="#" class="js_sml_send_link" id="<bean:write name="usrMdl" property="usrSid" />">
              <img class="btn_classicImg-display wp16hp18" src="../common/images/classic/icon_smail.gif" alt="<gsmsg:write key="cmn.shortmail" />">
              <img class="btn_originalImg-display wp16hp18" src="../schedule/images/original/plugin_smail.png" alt="<gsmsg:write key="cmn.shortmail" />">
              <gsmsg:write key="cmn.shortmail" />
            </a><br>
          </logic:equal>
        </logic:equal>
        <logic:equal name="sch010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
          <!-- 在席 -->
          <% String zaisekiClass = ""; %>
          <% String zaisekiSelected = ""; %>
          <% String fuzaiSelected = ""; %>
          <% String sonotaSelected = ""; %>
          <logic:equal name="usrMdl" property="zaisekiKbn" value="1">
            <% zaisekiClass = "zaiseki_select-zaiseki"; %>
            <% zaisekiSelected = "selected"; %>
          </logic:equal>
          <!-- 不在 -->
          <logic:equal name="usrMdl" property="zaisekiKbn" value="2">
            <% zaisekiClass = "zaiseki_select-huzai"; %>
            <% fuzaiSelected = "selected"; %>
          </logic:equal>
          <!-- その他 -->
          <logic:equal name="usrMdl" property="zaisekiKbn" value="0">
            <% zaisekiClass = "zaiseki_select-sonota"; %>
            <% sonotaSelected = "selected"; %>
          </logic:equal>
          <select class="wp65 mt5 <%= zaisekiClass %>" onchange="setZaisekiStatus(this, <bean:write name="usrMdl" property="usrSid" />);">
            <option value="sch010Zaiseki" <%= zaisekiSelected %>><gsmsg:write key="cmn.zaiseki" /></option>
            <option value="sch010Fuzai" <%= fuzaiSelected %>><gsmsg:write key="cmn.absence" /></option>
            <option value="sch010Sonota" <%= sonotaSelected %>><gsmsg:write key="cmn.other" /></option>
          </select>
        </logic:equal>
        <logic:notEqual name="sch010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
        </logic:notEqual>
      </span>
      </td>
      </logic:notEqual>
      <bean:define id="sch010UsrSid" name="usrMdl" property="usrSid" />
      <bean:define id="mySchedule" value="0" />
      <logic:notEmpty name="sch010Form" property="sch010UserSchedule">
        <bean:define id="mySchedule" value="1" />
      </logic:notEmpty>
      <%
        if (String.valueOf(sessionUsrSid).equals(String.valueOf(sch010UsrSid)) && String.valueOf(mySchedule).equals("1")) { %>
        <!-- スケジュールリスト内のログインユーザのスケジュール --> 
        <jsp:include page="/WEB-INF/plugin/schedule/jsp/sch010_mySelfInList.jsp" >
          <jsp:param value="<%= editConfOwn %>" name="editConfOwn"/>
          <jsp:param value="<%= editConfGroup %>" name="editConfGroup"/>
          <jsp:param value="<%= dspPublic %>" name="dspPublic"/>
          <jsp:param value="<%= project %>" name="project"/>
          <jsp:param value="<%= nippou %>" name="nippou"/>
          <jsp:param value="<%= grpHeight %>" name="grpHeight"/>
          <jsp:param value="<%= schTipCnt %>" name="schTipCnt"/>
        </jsp:include>
      <% } else { %>
      <!-- グループメンバースケジュール(ログインユーザ以外)のスケジュール情報 -->
      <logic:notEmpty name="gpWeekMdl" property="sch010SchList">
        <logic:iterate id="gpDayMdl" name="gpWeekMdl" property="sch010SchList" type="jp.groupsession.v2.sch.sch010.Sch010DayOfModel">
        <%
          String detailId = gpDayMdl.getSchDate() + "-";
          if (gpDayMdl.getUsrKbn() == 1) {
            detailId += "G";
          }
          detailId += gpDayMdl.getUsrSid();
        %>
          <logic:equal name="gpDayMdl" property="weekKbn" value="1">
            <logic:equal name="gpDayMdl" property="todayKbn" value="1">
              <td class="w12 bgC_tableCell txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
            </logic:equal>
            <logic:notEqual name="gpDayMdl" property="todayKbn" value="1">
              <td class="w12 bgC_tableCell_Sunday txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
              <input type="hidden" name="sch010CellClass" value="bgC_tableCell_Sunday" id="sch010BaseClass_<%= detailId %>">
            </logic:notEqual>
          </logic:equal>
          <logic:equal name="gpDayMdl" property="weekKbn" value="7">
            <logic:equal name="gpDayMdl" property="todayKbn" value="1">
              <td class="w12 bgC_tableCell txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
            </logic:equal>
            <logic:notEqual name="gpDayMdl" property="todayKbn" value="1">
              <td class="w12 bgC_tableCell_Saturday txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
              <input type="hidden" name="sch010CellClass" value="bgC_tableCell_Saturday" id="sch010BaseClass_<%= detailId %>">
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="gpDayMdl" property="weekKbn" value="1">
            <logic:notEqual name="gpDayMdl" property="weekKbn" value="7">
              <logic:equal name="gpDayMdl" property="todayKbn" value="1">
                <td class="w12 bgC_tableCell txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
              </logic:equal>
              <logic:notEqual name="gpDayMdl" property="todayKbn" value="1">
                <td class="w12 bgC_tableCell txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
              </logic:notEqual>
            </logic:notEqual>
          </logic:notEqual>
          <logic:equal name="usrMdl" property="schRegistFlg" value="true">
            <a href="#" class="js_schAddBtn" onClick="return addSchedule('add', <bean:write name="gpDayMdl" property="schDate" />, <bean:write name="gpDayMdl" property="usrSid" />, <bean:write name="gpDayMdl" property="usrKbn" />);">
              <img class="btn_classicImg-display eventImg" src="../common/images/classic/icon_scadd.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display eventImg" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            </a>
            <span class="js_easyRegister js_schAddBtn cursor_pointer">
              <img class="eventImg wp18  ml5" src="../common/images/original/bubble_pen_icon.png" alt="<gsmsg:write key="cmn.add" />">
              <div class="display_none js_schDate"><bean:write name="gpDayMdl" property="schDate" /></div>
              <div class="display_none js_schUsrSid"><bean:write name="gpDayMdl" property="usrSid" /></div>
              <div class="display_none js_schUsrKbn"><bean:write name="gpDayMdl" property="usrKbn" /></div>
            </span>
            <html:multibox name="sch010Form" property="schIkkatuTorokuKey" onclick="sch010IkkatsuCheck(this)" styleClass="js_schIkkatsuCheck display_none">
              <%= detailId %>
            </html:multibox>
          </logic:equal>
          <logic:notEmpty name="gpDayMdl" property="holidayName">
            <bean:define id="grpSid" name="sch010Form" property="sch010DspGpSid" />
            <%
              if (String.valueOf(grpSid).substring(0, 1).equals("H")
                  && cnt == 0) {
            %>
                  <span class="to flo_r fs_11 cl_fontWarn"><bean:write name="gpDayMdl" property="holidayName" /></span>
            <%
              }
            %>
          </logic:notEmpty>
          <logic:notEmpty name="gpDayMdl" property="schDataList">
            <logic:iterate id="gpSchMdl" name="gpDayMdl" property="schDataList">
              <bean:define id="u_usrsid" name="gpDayMdl" property="usrSid" type="java.lang.Integer" />
              <bean:define id="u_schsid" name="gpSchMdl" property="schSid" type="java.lang.Integer" />
              <bean:define id="u_date" name="gpDayMdl" property="schDate"  type="java.lang.String" />
              <bean:define id="type1" name="gpSchMdl" property="public"  type="java.lang.Integer" />
              <bean:define id="type2" name="gpSchMdl" property="kjnEdKbn"  type="java.lang.Integer" />
              <bean:define id="type3" name="gpSchMdl" property="grpEdKbn"  type="java.lang.Integer" />
              <%
                int publicType = ((Integer)pageContext.getAttribute("type1",PageContext.PAGE_SCOPE));
                int kjnEdKbn = ((Integer)pageContext.getAttribute("type2",PageContext.PAGE_SCOPE));
                int grpEdKbn = ((Integer)pageContext.getAttribute("type3",PageContext.PAGE_SCOPE));
              %>
              <!--公開-->
              <%
                if ((publicType == dspPublic || publicType == dspBelongGroup) || (kjnEdKbn == editConfOwn || grpEdKbn == editConfGroup)) {
              %>
              <logic:empty name="gpSchMdl" property="valueStr">
                <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="gpDayMdl" property="schDate" />, <bean:write name="gpSchMdl" property="schSid" />, <bean:write name="gpDayMdl" property="usrSid" />, <bean:write name="gpDayMdl" property="usrKbn" />);">
                  <span class="tooltips">
                    <bean:write name="gpSchMdl" property="title" />
                  </span>
                  <div class="cal_space">
              </logic:empty>
              <logic:notEmpty name="gpSchMdl" property="valueStr">
                <bean:define id="scnaiyou" name="gpSchMdl" property="valueStr" />
                <%
                  String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                  String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                %>
                <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="gpDayMdl" property="schDate" />, <bean:write name="gpSchMdl" property="schSid" />, <bean:write name="gpDayMdl" property="usrSid" />, <bean:write name="gpDayMdl" property="usrKbn" />);">
                  <span class="tooltips">
                    <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                  </span>
                  <div class="cal_space">
              </logic:notEmpty>
              <!--タイトルカラー設定-->
              <logic:equal name="gpSchMdl" property="bgColor" value="0">
                <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="gpSchMdl" property="bgColor" value="1">
                <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="gpSchMdl" property="bgColor" value="2">
                <div class="cl_fontSchTitleRed opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="gpSchMdl" property="bgColor" value="3">
                <div class="cl_fontSchTitleGreen opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="gpSchMdl" property="bgColor" value="4">
                <div class="cl_fontSchTitleYellow opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="gpSchMdl" property="bgColor" value="5">
                <div class="cl_fontSchTitleBlack opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="gpSchMdl" property="bgColor" value="6">
                <div class="cl_fontSchTitleNavy opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="gpSchMdl" property="bgColor" value="7">
                <div class="cl_fontSchTitleWine opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="gpSchMdl" property="bgColor" value="8">
                <div class="cl_fontSchTitleCien opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="gpSchMdl" property="bgColor" value="9">
                <div class="cl_fontSchTitleGray opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="gpSchMdl" property="bgColor" value="10">
                <div class="cl_fontSchTitleMarine opacity6-hover fs_13">
              </logic:equal>
              <div class="cal_content-space">
                <logic:notEmpty name="gpSchMdl" property="time">
                  <span class="cal_time">
                    <bean:write name="gpSchMdl" property="time" />
                  </span>
                  </div>
                </logic:notEmpty>
                <logic:empty name="gpSchMdl" property="time">
                  <div class="mt10"></div>
                </logic:empty>
                <div class="cal_content">
                  <logic:equal name="gpSchMdl" property="publicIconFlg" value="true">
                     <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                     <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                  </logic:equal>
                  <bean:write name="gpSchMdl" property="title" />
                </div>
              </div>
              </div>
              </div>
              </a>
              <%
                } else {
              %>
              <!--非公開-->
              <span class="fs_13">
                <div class="cal_space">
                <div class="cal_content-space">
                  <logic:notEmpty name="gpSchMdl" property="time">
                    <span class="cal_time">
                      <bean:write name="gpSchMdl" property="time" />
                    </span>
                  </logic:notEmpty>
                  <logic:notEmpty name="gpSchMdl" property="title">
                    <div class="cal_content">
                      <logic:equal name="gpSchMdl" property="publicIconFlg" value="true">
                         <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                         <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                      </logic:equal>
                      <bean:write name="gpSchMdl" property="title" />
                    </div>
                  </logic:notEmpty>
                </div>
                </div>
              </span>
              </a>
              <%
                }
              %>
            </logic:iterate>
          </logic:notEmpty>
          </td>
        </logic:iterate>
      </logic:notEmpty>
    </tr>
    <!--期間スケジュール-->
    <logic:notEmpty name="gpWeekMdl" property="sch010NoTimeSchList">
      <bean:define id="memPrList" name="gpWeekMdl" property="sch010NoTimeSchList" type="java.util.ArrayList"/>
      <% int rowSize = memPrList.size(); %>
      <logic:iterate id="periodList" name="gpWeekMdl" property="sch010NoTimeSchList" indexId="rowPidx">
        <logic:notEmpty name="periodList">
          <tr>
            <bean:define id="prList" name="periodList" type="java.util.ArrayList"/>
            <% int size = prList.size(); %>
            <logic:iterate id="gpPeriodMdl" name="periodList" indexId="memPidx">
              <logic:notEmpty name="gpPeriodMdl" property="periodMdl">
                <bean:define id="pMdl" name="gpPeriodMdl" property="periodMdl"/>
                <td class="cal_periodCell" colspan="<bean:write name="pMdl" property="schPeriodCnt" />">
                  <bean:define id="p_schsid" name="gpPeriodMdl" property="schSid" type="java.lang.Integer" />
                  <bean:define id="p_public" name="gpPeriodMdl" property="public"  type="java.lang.Integer" />
                  <bean:define id="p_kjnEdKbn" name="gpPeriodMdl" property="kjnEdKbn"  type="java.lang.Integer" />
                  <bean:define id="p_grpEdKbn" name="gpPeriodMdl" property="grpEdKbn"  type="java.lang.Integer" />
                  <%
                    int publicType = ((Integer)pageContext.getAttribute("p_public",PageContext.PAGE_SCOPE));
                    int kjnEdKbn = ((Integer)pageContext.getAttribute("p_kjnEdKbn",PageContext.PAGE_SCOPE));
                    int grpEdKbn = ((Integer)pageContext.getAttribute("p_grpEdKbn",PageContext.PAGE_SCOPE));
                  %>
                  <!--公開-->
                  <%
                  if ((publicType == dspPublic || publicType == dspBelongGroup) || (kjnEdKbn == editConfOwn || grpEdKbn == editConfGroup)) {
                  %>
                  <logic:empty name="gpPeriodMdl" property="schAppendUrl">
                    <logic:empty name="gpPeriodMdl" property="valueStr">
                      <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="gpDayMdl" property="schDate" />, <bean:write name="gpPeriodMdl" property="schSid" />, <bean:write name="gpDayMdl" property="usrSid" />, <bean:write name="gpDayMdl" property="usrKbn" />);">
                        <span class="tooltips">
                          <bean:write name="gpPeriodMdl" property="title" />
                        </span>
                    </logic:empty>
                    <logic:notEmpty name="gpPeriodMdl" property="valueStr">
                      <bean:define id="scnaiyou" name="gpPeriodMdl" property="valueStr" />
                      <%
                        String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                        String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                      %>
                      <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="gpDayMdl" property="schDate" />, <bean:write name="gpPeriodMdl" property="schSid" />, <bean:write name="gpDayMdl" property="usrSid" />, <bean:write name="gpDayMdl" property="usrKbn" />);">
                        <span class="tooltips">
                          <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                        </span>
                    </logic:notEmpty>
                  </logic:empty>
                  <logic:notEmpty name="gpPeriodMdl" property="schAppendUrl">
                    <logic:empty name="gpPeriodMdl" property="valueStr">
                      <a id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" href="<bean:write name="gpPeriodMdl" property="schAppendUrl" />">
                        <% boolean schFilter = true; %>
                        <logic:equal name="gpPeriodMdl" property="userKbn" value="<%= project %>">
                          <% schFilter = false; %>
                        </logic:equal>
                        <span class="tooltips">
                          <bean:write name="gpPeriodMdl" property="title" filter="<%= schFilter %>" />
                        </span>
                    </logic:empty>
                    <logic:notEmpty name="gpPeriodMdl" property="valueStr">
                      <bean:define id="scnaiyou" name="gpPeriodMdl" property="valueStr" />
                      <%
                        String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                        String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                      %>
                      <a id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" href="<bean:write name="gpPeriodMdl" property="schAppendUrl" />">
                        <span class="tooltips">
                          <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                        </span>
                    </logic:notEmpty>
                  </logic:notEmpty>
                  <!--タイトルカラー設定-->
                  <logic:equal name="gpPeriodMdl" property="bgColor" value="0">
                    <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gpPeriodMdl" property="bgColor" value="1">
                    <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gpPeriodMdl" property="bgColor" value="2">
                    <div class="cl_fontSchTitleRed opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gpPeriodMdl" property="bgColor" value="3">
                    <div class="cl_fontSchTitleGreen opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gpPeriodMdl" property="bgColor" value="4">
                    <div class="cl_fontSchTitleYellow opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gpPeriodMdl" property="bgColor" value="5">
                    <div class="cl_fontSchTitleBlack opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gpPeriodMdl" property="bgColor" value="6">
                    <div class="cl_fontSchTitleNavy opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gpPeriodMdl" property="bgColor" value="7">
                    <div class="cl_fontSchTitleWine opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gpPeriodMdl" property="bgColor" value="8">
                    <div class="cl_fontSchTitleCien opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gpPeriodMdl" property="bgColor" value="9">
                    <div class="cl_fontSchTitleGray opacity6-hover fs_13">
                  </logic:equal>
                  <logic:equal name="gpPeriodMdl" property="bgColor" value="10">
                    <div class="cl_fontSchTitleMarine opacity6-hover fs_13">
                  </logic:equal>
                  <% boolean schFilter = true; %>
                  <div class="cal_todoSpace">
                    <logic:equal name="gpPeriodMdl" property="userKbn" value="<%= project %>">
                      <span class="cal_label-todo">TODO</span>
                      <% schFilter = false; %>
                    </logic:equal>
                    <logic:equal name="gpPeriodMdl" property="userKbn" value="<%= nippou %>">
                      <span class="cal_label-action">アクション</span>
                    </logic:equal>
                    <logic:notEmpty name="gpPeriodMdl" property="time">
                      <font size="-2"><bean:write name="gpPeriodMdl" property="time" /><br></font>
                    </logic:notEmpty>
                    <logic:equal name="gpPeriodMdl" property="publicIconFlg" value="true">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                    </logic:equal>
                    <bean:write name="gpPeriodMdl" property="title" filter="<%= schFilter %>" /><br>
                  </div>
                  <%
                    } else {
                  %>
                  <!--非公開-->
                  <span class="fs_13">
                    <logic:notEmpty name="gpPeriodMdl" property="time">
                      <font size="-2"><bean:write name="gpPeriodMdl" property="time" /><br></font>
                    </logic:notEmpty>
                    <logic:notEmpty name="gpPeriodMdl" property="title">
                      <bean:write name="gpPeriodMdl" property="title" />
                    </logic:notEmpty>
                  </span>
                  <%
                    }
                  %>
                  </div>
                </td>
              </logic:notEmpty>
              <logic:empty name="gpPeriodMdl" property="periodMdl">
                <td class="cal_periodCell-less"></td>
              </logic:empty>
            </logic:iterate>
          </tr>
        </logic:notEmpty>
      </logic:iterate>
    </logic:notEmpty>
    <%
      }
    %>
  </logic:iterate>