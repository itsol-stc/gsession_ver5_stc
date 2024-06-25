<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleReadOnly.tld" prefix="dailySchedule" %>
<%@ taglib uri="/WEB-INF/ctag-dailyReserveReadOnly.tld" prefix="dailyReserve" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
  <head>
    <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
    <meta name="format-detection" content="telephone=no">
    <title>GROUPSESSION <gsmsg:write key="schedule.sch120.1" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <gsjsmsg:js filename="gsjsmsg.js"/>
    <script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../schedule/js/sch120.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script type="text/javascript">
    <!--
      //自動リロード
      <logic:notEqual name="sch120Form" property="sch120Reload" value="0">
        var reloadinterval = <bean:write name="sch120Form" property="sch120Reload" />;
        setTimeout("buttonPush('reload')",reloadinterval);
      </logic:notEqual>
    -->
    </script>

    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
    <link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

  </head>

  <body onload="window_create();" onunload="windowClose();calWindowClose();" onkeydown="return keyPress(event.keyCode);" class="m0">
    <html:form action="/schedule/sch120">
      <input type="hidden" name="CMD" value="">
      <html:hidden property="sch010SelectUsrSid" />
      <html:hidden property="sch010SelectUsrKbn" />

      <html:hidden property="cmd" />
      <html:hidden property="dspMod" />
      <html:hidden property="sch010DspDate" />
      <html:hidden property="changeDateFlg" />
      <html:hidden property="iniDsp" />
      <html:hidden property="sch010DspGpSid" />

      <html:hidden property="sch010SelectDate" />
      <html:hidden property="sch010SchSid" />
      <html:hidden property="sch120MoveMode" />
      <html:hidden property="sch120FromHour" />
      <html:hidden property="sch120ResDspGpSid" />

      <logic:notEmpty name="sch120Form" property="sv_users" scope="request">
        <logic:iterate id="svuBean" name="sch120Form" property="sv_users" scope="request">
          <input type="hidden" name="sv_users" value="<bean:write name="svuBean" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="sch120Form" property="users_l" scope="request">
        <logic:iterate id="ulBean" name="sch120Form" property="users_l" scope="request">
          <input type="hidden" name="users_l" value="<bean:write name="ulBean" />">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch120Form" property="svReserveUsers" scope="request">
        <logic:iterate id="svrBean" name="sch120Form" property="svReserveUsers" scope="request">
          <input type="hidden" name="svReserveUsers" value="<bean:write name="svrBean" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="sch120Form" property="reserve_l" scope="request">
        <logic:iterate id="rlBean" name="sch120Form" property="reserve_l" scope="request">
          <input type="hidden" name="reserve_l" value="<bean:write name="rlBean" />">
        </logic:iterate>
      </logic:notEmpty>

      <bean:define id="fromHour" name="sch120Form" property="sch120FromHour" scope="request"/>
      <bean:define id="toHour" name="sch120Form" property="sch120ToHour" scope="request"/>
      <bean:define id="dspDate" name="sch120Form" property="sch010DspDate" scope="request"/>
      <bean:define id="totalCols" name="sch120Form" property="sch120TotalCols" scope="request"/>
      <bean:define id="adminKbn" name="sch120Form" property="adminKbn" scope="request"/>
      <bean:define id="memoriCount" name="sch120Form" property="sch120MemoriCount" scope="request"/>
      <bean:define id="zskUseKbn" name="sch120Form" property="zaisekiUseOk" scope="request"/>


      <!--　BODY -->

      <div class="js_sch_top_frame topFrame-fixed m0">
        <div class="pageTitle">
          <ul>
            <li>
              <img class="header_pluginImg-classic" src="../schedule/images/classic/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
              <img class="header_pluginImg" src="../schedule/images/original/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
            </li>
            <li>
              <gsmsg:write key="schedule.108" />
            </li>
            <li class="pageTitle_subFont">
              <gsmsg:write key="schedule.sch120.4" />
            </li>
            <li>
              <div>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.reload" />" onClick="buttonPush('reload');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
                  <gsmsg:write key="cmn.reload" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
                  <gsmsg:write key="cmn.close" />
                </button>
              </div>
            </li>
          </ul>
        </div>

        <div class="wrapper">
          <bean:size id="topSize" name="sch120Form" property="sch010TopList" scope="request" />
          <logic:equal name="topSize" value="2">
          <logic:iterate id="weekBean" name="sch120Form" property="sch010TopList" scope="request" offset="1"/>
          </logic:equal>
          <logic:notEqual name="topSize" value="2">
          <logic:iterate id="weekBean" name="sch120Form" property="sch010TopList" scope="request" offset="0"/>
          </logic:notEqual>

          <bean:define id="usrBean" name="weekBean" property="sch010UsrMdl" />
          <div class="verAlignMid w100">
            <logic:equal name="sch120Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
              <span class="cal_colHeader-zaiseki borC_light bor1 p5 mr5" ><gsmsg:write key="cmn.zaiseki" /></span>
              <span class="cal_colHeader-huzai borC_light bor1 p5 mr5" ><gsmsg:write key="cmn.absence" /></span>
              <span class="cal_colHeader-sonota borC_light bor1 p5" ><gsmsg:write key="cmn.other" /></span>
            </logic:equal>
            <div class="mrl_auto"></div>
            <button type="button" class="webIconBtn" onClick="buttonPush('move_ld');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="buttonPush('today');">
              <gsmsg:write key="cmn.today" />
            </button>
            <span class="original-display">
              <a href="#" class="fw_b todayBtn " onClick="buttonPush('today');">
                <gsmsg:write key="cmn.today" />
              </a>
            </span>

            <button type="button" class="webIconBtn" onClick="buttonPush('move_rd');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
            <button type="button" class="iconBtn-border" value="Cal"  onClick="wrtCalendarByBtn(this.form.sch010DspDate, 'sch120');" id="sch120">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
              <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
            </button>
          </div>


          <table class="table-top cal_table2 w100 mb0">
            <tr>
              <th class="w100 table_title-color txt_l">
                <bean:write name="sch120Form" property="sch120StrDate" scope="request" />
                <bean:write name="sch120Form" property="schDispRokuyou" scope="request" />
              </th>
            </tr>
          </table>
        </div>
      </div>

      <table class="w100">
        <tr>
          <td class="wrapper_space">
            <div class="wrapper">
              <table class="table-top js_sch_under_frame w100">
                <!-- タイトル行(氏名,新規,時間) -->
                <tr class="cal_day_hd">
                  <td class="cal_header wp120 mwp120" colspan="2" rowspan="2"><gsmsg:write key="cmn.name" /></td>

                  <logic:notEmpty name="sch120Form" property="sch120TimeChartList" scope="request">
                    <logic:iterate id="strHour" name="sch120Form" property="sch120TimeChartList" scope="request">
                      <td class="cal_header" colspan="<bean:write name="memoriCount" scope="page" />"><span><bean:write name="strHour" scope="page" /></span></td>
                    </logic:iterate>
                  </logic:notEmpty>
                </tr>

                <!-- 時間目盛 -->
                <tr class="sch_scale_area">
                  <logic:notEmpty name="sch120Form" property="sch120TimeChartList" scope="request">
                    <logic:iterate id="strHour" name="sch120Form" property="sch120TimeChartList" scope="request">

                      <logic:equal name="memoriCount" value="4">
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                      </logic:equal>

                      <logic:equal name="memoriCount" value="6">
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                      </logic:equal>

                      <logic:equal name="memoriCount" value="12">
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                        <td class="sch_timeScale"></td>
                      </logic:equal>

                    </logic:iterate>
                  </logic:notEmpty>
                </tr>

                <!-- グループと本人 -->
                <logic:notEmpty name="sch120Form" property="sch010TopList" scope="request">
                  <logic:iterate id="weekMdl" name="sch120Form" property="sch010TopList" scope="request">
                    <tr class="txt_l txt_t bgC_tableCell">
                      <dailySchedule:dailywrite name="weekMdl" top="1" from="<%= fromHour.toString() %>" to="<%= toHour.toString() %>" admin="<%= adminKbn.toString() %>" memCnt="<%= memoriCount.toString() %>"/>
                    </tr>
                  </logic:iterate>
                </logic:notEmpty>

                <!-- グループメンバー -->
                <logic:notEmpty name="sch120Form" property="sch010BottomList" scope="request">
                  <logic:iterate id="gpWeekMdl" name="sch120Form" property="sch010BottomList" scope="request" indexId="cnt">

                    <bean:define id="ret" value="<%= String.valueOf(cnt.intValue() % 5) %>" />
                    <logic:equal name="ret" value="0">
                      <!-- タイトル行(氏名,新規,時間) -->
                      <tr class="cal_day_hd">
                        <td class="cal_header wp120 mwp120" colspan="2" rowspan="2"><gsmsg:write key="cmn.name" /></td>
                        <!-- 時間目盛 -->
                        <logic:notEmpty name="sch120Form" property="sch120TimeChartList" scope="request">
                          <logic:iterate id="strHour" name="sch120Form" property="sch120TimeChartList" scope="request">
                            <td class="cal_header " colspan="<bean:write name="memoriCount" scope="page" />"><bean:write name="strHour" scope="page" /></td>
                          </logic:iterate>
                        </logic:notEmpty>
                      </tr>
                      <!-- 分目盛 -->
                      <tr class="sch_scale_area">
                        <logic:notEmpty name="sch120Form" property="sch120TimeChartList" scope="request">
                          <logic:iterate id="strHour" name="sch120Form" property="sch120TimeChartList" scope="request">
                            <logic:equal name="memoriCount" value="4">
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                            </logic:equal>

                            <logic:equal name="memoriCount" value="6">
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                            </logic:equal>

                            <logic:equal name="memoriCount" value="12">
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                              <td class="sch_timeScale"></td>
                            </logic:equal>
                          </logic:iterate>
                        </logic:notEmpty>
                      </tr>
                    </logic:equal>

                    <tr class="txt_l txt_t bgC_tableCell">
                      <dailySchedule:dailywrite name="gpWeekMdl" top="0" from="<%= fromHour.toString() %>" to="<%= toHour.toString() %>" admin="<%= adminKbn.toString() %>" memCnt="<%= memoriCount.toString() %>" />
                    </tr>

                  </logic:iterate>
                </logic:notEmpty>

                <!--施設予約状況-->

                <logic:notEmpty name="sch120Form" property="rsv020DaylyList" scope="request">
                  <logic:iterate id="sisetu" name="sch120Form" property="rsv020DaylyList" scope="request" indexId="cnt">
                    <bean:define id="ret" value="<%= String.valueOf(cnt.intValue() % 5) %>" />
                    <logic:equal name="ret" value="0">
                      <tr class="cal_day_hd">
                        <td class="cal_header wp120 mwp120" colspan="2" rowspan="2">
                          <gsmsg:write key="cmn.facility.name" />
                        </td>
                        <logic:notEmpty name="sch120Form" property="rsv020TimeChartList" scope="request">
                          <logic:iterate id="strHour" name="sch120Form" property="rsv020TimeChartList" scope="request">
                            <td class="cal_header" colspan='<bean:write name="memoriCount" scope="page" />'>
                              <bean:write name="strHour" scope="page" />
                            </td>
                          </logic:iterate>
                        </logic:notEmpty>
                      </tr>
                      <tr class="sch_scale_area">
                        <logic:iterate id="timeSpc" name="sch120Form" property="rsv020TimeChartList" scope="request">
                          <logic:equal name="memoriCount" value="4">
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                          </logic:equal>

                          <logic:equal name="memoriCount" value="6">
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                          </logic:equal>

                          <logic:equal name="memoriCount" value="12">
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                            <td class="sch_timeScale"></td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>
                    </logic:equal>
                    <tr  class="txt_l txt_t bgC_tableCell"  >
                      <dailyReserve:dailywrite name="sisetu" from="<%= fromHour.toString() %>" to="<%= toHour.toString() %>" dspDate="<%= dspDate.toString() %>" count="<%= memoriCount.toString() %>" zskKbn="<%= zskUseKbn.toString() %>"  />
                    </tr>
                  </logic:iterate>
                </logic:notEmpty>
              </table>
            </div>
        </td>
      </tr>
    </table>
    </html:form>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
  </body>
</html:html>