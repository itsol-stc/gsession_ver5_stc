<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstSchedule" %>
<!DOCTYPE html>

<html:html>
  <head>
    <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
    <meta name="format-detection" content="telephone=no">
    <title>GROUPSESSION <gsmsg:write key="schedule.sch041kn.5" /></title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <gsjsmsg:js filename="gsjsmsg.js"/>
    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../schedule/js/sch041kn.js?<%= GSConst.VERSION_PARAM %>"></script>

    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
    <link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

  </head>

  <body>

    <html:form action="/schedule/sch041kn">
      <html:hidden property="cmd" />
      <html:hidden property="dspMod" />
      <html:hidden property="listMod" />
      <html:hidden property="sch010DspDate" />
      <html:hidden property="changeDateFlg" />
      <html:hidden property="iniDsp" />
      <html:hidden property="sch010SelectUsrSid" />
      <html:hidden property="sch010SelectUsrKbn" />
      <html:hidden property="sch010SelectDate" />
      <html:hidden property="sch010SchSid" />
      <html:hidden property="sch010DspGpSid" />
      <html:hidden property="sch020SelectUsrSid" />
      <html:hidden property="sch030FromHour" />
      <html:hidden property="schWeekDate" />
      <html:hidden property="schDailyDate" />
      <html:hidden property="sch100SelectUsrSid" />
      <html:hidden property="sch100PageNum" />
      <html:hidden property="sch100Slt_page1" />
      <html:hidden property="sch100Slt_page2" />
      <html:hidden property="sch100OrderKey1" />
      <html:hidden property="sch100SortKey1" />
      <html:hidden property="sch100OrderKey2" />
      <html:hidden property="sch100SortKey2" />
      <html:hidden property="sch100SvOrderKey1" />
      <html:hidden property="sch100SvSortKey1" />
      <html:hidden property="sch100SvOrderKey2" />
      <html:hidden property="sch100SvSortKey2" />

      <html:hidden property="sch100SvSltGroup" />
      <html:hidden property="sch100SvSltUser" />
      <html:hidden property="sch100SvSltStartYearFr" />
      <html:hidden property="sch100SvSltStartMonthFr" />
      <html:hidden property="sch100SvSltStartDayFr" />
      <html:hidden property="sch100SvSltStartYearTo" />
      <html:hidden property="sch100SvSltStartMonthTo" />
      <html:hidden property="sch100SvSltStartDayTo" />
      <html:hidden property="sch100SvSltEndYearFr" />
      <html:hidden property="sch100SvSltEndMonthFr" />
      <html:hidden property="sch100SvSltEndDayFr" />
      <html:hidden property="sch100SvSltEndYearTo" />
      <html:hidden property="sch100SvSltEndMonthTo" />
      <html:hidden property="sch100SvSltEndDayTo" />
      <html:hidden property="sch100SvKeyWordkbn" />
      <html:hidden property="sch100SvKeyValue" />
      <html:hidden property="sch100SltGroup" />
      <html:hidden property="sch100SltUser" />
      <html:hidden property="sch100SltStartYearFr" />
      <html:hidden property="sch100SltStartMonthFr" />
      <html:hidden property="sch100SltStartDayFr" />
      <html:hidden property="sch100SltStartYearTo" />
      <html:hidden property="sch100SltStartMonthTo" />
      <html:hidden property="sch100SltStartDayTo" />
      <html:hidden property="sch100SltEndYearFr" />
      <html:hidden property="sch100SltEndMonthFr" />
      <html:hidden property="sch100SltEndDayFr" />
      <html:hidden property="sch100SltEndYearTo" />
      <html:hidden property="sch100SltEndMonthTo" />
      <html:hidden property="sch100SltEndDayTo" />
      <html:hidden property="sch100KeyWordkbn" />

      <logic:notEmpty name="sch041knForm" property="sch100SelectScdSid" scope="request">
        <logic:iterate id="selectScdSid" name="sch041knForm" property="sch100SelectScdSid" scope="request">
          <input type="hidden" name="sch100SelectScdSid" value="<bean:write name="selectScdSid"/>">
        </logic:iterate>
      </logic:notEmpty>

      <html:hidden property="sch010searchWord" />
      <html:hidden property="sch041ReminderTime" />
      <html:hidden property="sch041TargetGroup" />


      <logic:notEmpty name="sch041knForm" property="sch100SvSearchTarget" scope="request">
        <logic:iterate id="svTarget" name="sch041knForm" property="sch100SvSearchTarget" scope="request">
          <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="sch041knForm" property="sch100SearchTarget" scope="request">
        <logic:iterate id="target" name="sch041knForm" property="sch100SearchTarget" scope="request">
          <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041knForm" property="sch100SvBgcolor" scope="request">
        <logic:iterate id="svBgcolor" name="sch041knForm" property="sch100SvBgcolor" scope="request">
          <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="sch041knForm" property="sch100Bgcolor" scope="request">
        <logic:iterate id="bgcolor" name="sch041knForm" property="sch100Bgcolor" scope="request">
          <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041knForm" property="sch100CsvOutField" scope="request">
        <logic:iterate id="csvOutField" name="sch041knForm" property="sch100CsvOutField" scope="request">
          <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
        </logic:iterate>
      </logic:notEmpty>

      <html:hidden property="sch040Bgcolor" />
      <html:hidden property="sch040Title" />
      <html:hidden property="sch040Value" />
      <html:hidden property="sch040Biko" />
      <html:hidden property="sch040Public" />
      <html:hidden property="sch040FrYear" />
      <html:hidden property="sch040FrMonth" />
      <html:hidden property="sch040FrDay" />
      <html:hidden property="sch040FrHour" />
      <html:hidden property="sch040FrMin" />
      <html:hidden property="sch040ToYear" />
      <html:hidden property="sch040ToMonth" />
      <html:hidden property="sch040ToDay" />
      <html:hidden property="sch040ToHour" />
      <html:hidden property="sch040ToMin" />
      <html:hidden property="sch040FrDate"/>
      <html:hidden property="sch040ToDate"/>
      <html:hidden property="sch040FrTime"/>
      <html:hidden property="sch040ToTime"/>
      <html:hidden property="sch040GroupSid" />
      <html:hidden property="sch040ReserveGroupSid" />
      <html:hidden property="sch040Edit" />
      <html:hidden property="sch040TimeKbn" />
      <html:hidden property="sch040InitFlg" />

      <html:hidden property="sch041ExtSid" />
      <html:hidden property="sch041ExtKbn" />
      <html:hidden property="sch041Week" />
      <html:hidden property="sch041Day" />
      <html:hidden property="sch041DayOfYearly" />
      <html:hidden property="sch041MonthOfYearly" />
      <html:hidden property="sch041DayOfMonth" />
      <html:hidden property="sch041ConfKbn" />
      <html:hidden property="sch041WeekOrDay" />
      <html:hidden property="sch041TranKbn" />
      <html:hidden property="sch041FrYear" />
      <html:hidden property="sch041FrMonth" />
      <html:hidden property="sch041FrDay" />
      <html:hidden property="sch041ToYear" />
      <html:hidden property="sch041ToMonth" />
      <html:hidden property="sch041ToDay" />
      <html:hidden property="sch041FrHour" />
      <html:hidden property="sch041FrMin" />
      <html:hidden property="sch041ToHour" />
      <html:hidden property="sch041ToMin" />
      <html:hidden property="sch041Bgcolor" />
      <html:hidden property="sch041Title" />
      <html:hidden property="sch041Value" />
      <html:hidden property="sch041Biko" />
      <html:hidden property="sch041Public" />
      <html:hidden property="sch041Edit" />
      <html:hidden property="sch041TimeKbn" />
      <html:hidden property="sch041BatchRef" />
      <html:hidden property="sch041GroupSid" />
      <html:hidden property="sch041ReserveGroupSid" />
      <html:hidden property="sch041InitFlg" />
      <html:hidden property="sch041FrDate" />
      <html:hidden property="sch041FrTime" />
      <html:hidden property="sch041ToDate" />
      <html:hidden property="sch041ToTime" />

      <html:hidden property="addressPluginKbn" />
      <html:hidden property="searchPluginKbn" />

      <html:hidden property="sch040ReminderTime" />
      <html:hidden property="sch040TargetGroup" />

      <input type="hidden" name="sch041knBinSid" />

      <html:hidden property="sch040contact" />
      <html:hidden property="sch041contact" />
      <logic:notEmpty name="sch041knForm" property="sch040CompanySid">
        <logic:iterate id="coId" name="sch041knForm" property="sch040CompanySid">
          <input type="hidden" name="sch040CompanySid" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>
      </div>

      <logic:notEmpty name="sch041knForm" property="sch040CompanyBaseSid">
        <logic:iterate id="coId" name="sch041knForm" property="sch040CompanyBaseSid">
          <input type="hidden" name="sch040CompanyBaseSid" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041knForm" property="sch040AddressId">
        <logic:iterate id="addressId" name="sch041knForm" property="sch040AddressId">
          <input type="hidden" name="sch040AddressId" value="<bean:write name="addressId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041knForm" property="sch041CompanySid">
        <logic:iterate id="coId" name="sch041knForm" property="sch041CompanySid">
          <input type="hidden" name="sch041CompanySid" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041knForm" property="sch041CompanyBaseSid">
        <logic:iterate id="coId" name="sch041knForm" property="sch041CompanyBaseSid">
          <input type="hidden" name="sch041CompanyBaseSid" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041knForm" property="sch041AddressId">
        <logic:iterate id="addressId" name="sch041knForm" property="sch041AddressId">
          <input type="hidden" name="sch041AddressId" value="<bean:write name="addressId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041knForm" property="sch041Dweek" scope="request">
        <logic:iterate id="selectWeek" name="sch041knForm" property="sch041Dweek" scope="request">
          <input type="hidden" name="sch041Dweek" value="<bean:write name="selectWeek" />">
        </logic:iterate>
      </logic:notEmpty>

      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
      <input type="hidden" name="helpPrm" value="<bean:write name="sch041knForm" property="sch010SelectUsrKbn" /><bean:write name="sch041knForm" property="cmd" />">

      <logic:notEmpty name="sch041knForm" property="sv_users" scope="request">
        <logic:iterate id="ulBean" name="sch041knForm" property="sv_users" scope="request">
          <input type="hidden" name="sv_users" value="<bean:write name="ulBean" />">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041knForm" property="sch041SvUsers" scope="request">
        <logic:iterate id="eulBean" name="sch041knForm" property="sch041SvUsers" scope="request">
          <input type="hidden" name="sch041SvUsers" value="<bean:write name="eulBean" />">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041knForm" property="sch041SvReserve" scope="request">
        <logic:iterate id="ersBean" name="sch041knForm" property="sch041SvReserve" scope="request">
          <input type="hidden" name="sch041SvReserve" value="<bean:write name="ersBean" />">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041knForm" property="svReserveUsers" scope="request">
        <logic:iterate id="rsBean" name="sch041knForm" property="svReserveUsers" scope="request">
          <input type="hidden" name="svReserveUsers" value="<bean:write name="rsBean" />">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041knForm" property="sch041DisplayTarget" scope="request">
        <logic:iterate id="targetSid" name="sch041knForm" property="sch041DisplayTarget" scope="request">
          <input type="hidden" name="sch041DisplayTarget" value="<bean:write name="targetSid" />">
        </logic:iterate>
      </logic:notEmpty>

      <logic:equal name="sch041knForm" property="sch010SelectUsrKbn" value="1">
        <html:hidden property="sch040Public" />
      </logic:equal>

      <div class="pageTitle w80 mrl_auto">
        <ul>
          <li>
            <img class="header_pluginImg-classic" src="../schedule/images/classic/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
            <img class="header_pluginImg" src="../schedule/images/original/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
          </li>
          <li>
            <gsmsg:write key="schedule.108" />
          </li>
          <li class="pageTitle_subFont">
            <gsmsg:write key="cmn.for.repert" />
          </li>
          <li>
            <div>
              <logic:equal name="sch041knForm" property="cmd" value="add">
                <input type="hidden" name="CMD" value="041kn_ok">
                <button type="button" class="baseBtn" value="<gsmsg:write key="man.final" />" onClick="buttonPush('041kn_commit', '<bean:write name="sch041knForm" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                  <gsmsg:write key="cmn.final" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back2" />" onClick="buttonPush('041kn_back', '<bean:write name="sch041knForm" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:equal>
              <logic:equal name="sch041knForm" property="cmd" value="edit">
                <input type="hidden" name="CMD" value="041kn_ok">
                <button type="button" class="baseBtn" value="<gsmsg:write key="man.final" />" onClick="buttonPush('041kn_commit', '<bean:write name="sch041knForm" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                  <gsmsg:write key="cmn.final" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back2" />" onClick="buttonPush('041kn_back', '<bean:write name="sch041knForm" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:equal>
              <logic:equal name="sch041knForm" property="cmd" value="del">
                <input type="hidden" name="CMD" value="041kn_ok">
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete2" />" onClick="buttonPush('041kn_del_ok', '<bean:write name="sch041knForm" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back2" />" onClick="buttonPush('041kn_back', 'edit');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:equal>
            </div>
          </li>
        </ul>
      </div>
      <div class="wrapper w80 mrl_auto">
        <logic:messagesPresent message="false">
          <html:errors/>
        </logic:messagesPresent>

          <bean:define id="tdColor" value="" />
          <logic:notEqual name="sch041knForm" property="sch010SelectUsrKbn" value="0">
            <bean:define id="tdColor" value="bgC_other1" />
          </logic:notEqual>
          <logic:equal name="sch041knForm" property="sch010SelectUsrKbn" value="0">
            <bean:define id="tdColor" value="bgC_tableCell" />
          </logic:equal>


          <table class="table-left w100">
            <!-- 名前 -->
            <tr>
              <th class="w20 txt_l"><gsmsg:write key="schedule.4" /></th>
              <td class="w80 <%= tdColor %>">
                <logic:notEqual name="sch041knForm" property="sch010SelectUsrKbn" value="0">
                  <span class="flo_l">
                    <img class="classic-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
                    <img class="original-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
                  </span>
                </logic:notEqual>
                <logic:equal name="sch041knForm" property="sch040UsrUkoFlg" value="1">
                  <span class="mukoUser">
                    <bean:write name="sch041knForm" property="sch040UsrName" />
                  </span>
                </logic:equal>
                <logic:notEqual name="sch041knForm" property="sch040UsrUkoFlg" value="1">
                  <bean:write name="sch041knForm" property="sch040UsrName" />
                </logic:notEqual>
              </td>
            </tr>
            <!-- 設定日付 -->
            <tr>
              <th class="w20 txt_l"><gsmsg:write key="cmn.setting" /><gsmsg:write key="cmn.date2" /></th>
              <td class="w80 <%= tdColor %>">
                <logic:notEqual name="sch041knForm" property="cmd" value="del">
                  <span class="cl_fontWarn"><gsmsg:write key="schedule.138" /></span><br>
                </logic:notEqual>
                <logic:equal name="sch041knForm" property="cmd" value="del">
                  <span class="cl_fontWarn"><gsmsg:write key="schedule.139" /></span><br>
                </logic:equal>
                <div class="display_inline">
                  <logic:equal name="sch041knForm" property="cmd" value="add">
                    <logic:notEmpty name="sch041knForm" property="sch041knAftDateList" scope="request">
                      <div>
                        <logic:iterate id="aftDate" name="sch041knForm" property="sch041knAftDateList" scope="request">
                          <bean:write name="aftDate" scope="page"/><br>
                        </logic:iterate>
                      </div>
                    </logic:notEmpty>
                  </logic:equal>

                  <logic:equal name="sch041knForm" property="cmd" value="edit">
                    <div>
                      <logic:notEmpty name="sch041knForm" property="sch041knBefDateList" scope="request">
                        【<gsmsg:write key="reserve.rsv111kn.7" />】<br>
                        <logic:iterate id="befDate" name="sch041knForm" property="sch041knBefDateList" scope="request">
                          <bean:write name="befDate" scope="page"/><br>
                        </logic:iterate>
                      </logic:notEmpty>
                    </div>
                    <div class="verAlignMid p5">
                      <img class="classic-display" src="../common/images/classic/icon_arrow_east.gif" alt="<gsmsg:write key="cmn.edit" />">
                      <img class="original-display" src="../common/images/original/icon_arrow_east.png" alt="<gsmsg:write key="cmn.edit" />">
                    </div>
                    <div>
                      <logic:notEmpty name="sch041knForm" property="sch041knAftDateList" scope="request">
                        【<gsmsg:write key="reserve.rsv111kn.8" />】<br>
                        <logic:iterate id="aftDate" name="sch041knForm" property="sch041knAftDateList" scope="request">
                          <bean:write name="aftDate" scope="page"/><br>
                        </logic:iterate>
                      </logic:notEmpty>
                    </div>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="cmd" value="del">
                    <logic:notEmpty name="sch041knForm" property="sch041knBefDateList" scope="request">
                      <div>
                        <logic:iterate id="befDate" name="sch041knForm" property="sch041knBefDateList" scope="request">
                          <bean:write name="befDate" scope="page"/><br>
                        </logic:iterate>
                      </div>
                    </logic:notEmpty>
                  </logic:equal>
                </div>
                <logic:notEqual name="sch041knForm" property="cmd" value="del">
                  <div>
                    <logic:equal name="sch041knForm" property="sch041TranKbn" value="9">
                    <span class="cl_fontWarn"><gsmsg:write key="schedule.sch041kn.1" /></span>
                    </logic:equal>
                    <logic:equal name="sch041knForm" property="sch041TranKbn" value="0">
                    <span class="cl_fontWarn"><gsmsg:write key="schedule.sch041kn.2" /></span>
                    </logic:equal>
                    <logic:equal name="sch041knForm" property="sch041TranKbn" value="1">
                    <span class="cl_fontWarn"><gsmsg:write key="schedule.sch041kn.3" /></span>
                    </logic:equal>
                    <logic:equal name="sch041knForm" property="sch041TranKbn" value="2">
                    <span class="cl_fontWarn"><gsmsg:write key="schedule.sch041kn.4" /></span>
                    </logic:equal>
                  </div>
                </logic:notEqual>
              </td>
            </tr>
            <!--設定期間 -->
            <tr>
              <th class="w20 txt_l"><gsmsg:write key="schedule.sch041.8" /></th>
              <td class="w80 <%= tdColor %>">
                <logic:notEqual name="sch041knForm" property="cmd" value="del">
                  <bean:write name="sch041knForm" property="sch041knFromDate" />
                  ～
                  <bean:write name="sch041knForm" property="sch041knToDate" />
                  </logic:notEqual>
                  <logic:equal name="sch041knForm" property="cmd" value="del">
                  <bean:write name="sch041knForm" property="sch041knDelFrDate" />
                  ～
                  <bean:write name="sch041knForm" property="sch041knDelToDate" />
                </logic:equal>
              </td>
            </tr>
            <!-- 時間 -->
            <tr>
              <th class="w20 txt_l"><gsmsg:write key="cmn.time" /></th>
              <td class="w80 <%= tdColor %>">
                <logic:notEqual name="sch041knForm" property="cmd" value="del">
                  <logic:equal name="sch041knForm" property="sch041TimeKbn" value="0">
                    <bean:write name="sch041knForm" property="sch041knFromTime" />
                    ～
                    <bean:write name="sch041knForm" property="sch041knToTime" />
                  </logic:equal>
                </logic:notEqual>
                <logic:equal name="sch041knForm" property="cmd" value="del">
                  <logic:equal name="sch041knForm" property="sch041TimeKbn" value="0">
                    <bean:write name="sch041knForm" property="sch041knDelFrTime" />
                    ～
                    <bean:write name="sch041knForm" property="sch041knDelToTime" />
                  </logic:equal>
                </logic:equal>
              </td>
            </tr>

            <logic:equal name="sch041knForm" property="addressPluginKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
              <!-- 会社担当者 -->
              <tr>
                <th class="w20 txt_l"><gsmsg:write key="schedule.14" /></td>
                <td class="w80 <%= tdColor %>">
                  <logic:notEmpty name="sch041knForm" property="sch041CompanyList">
                    <logic:iterate id="companyData" name="sch041knForm" property="sch041CompanyList">
                      <div class="verAlignMid">
                        <img class="classic-display" src="../common/images/classic/icon_company.png" alt="<gsmsg:write key="address.139" />">
                        <img class="original-display" src="../common/images/original/icon_company.png" alt="<gsmsg:write key="address.139" />">
                        <bean:write name="companyData" property="companyName" />
                      </div><br>
                      <logic:notEmpty name="companyData" property="addressDataList">
                      <div class="ml10">
                        <logic:iterate id="addressData" name="companyData" property="addressDataList">
                          <div class="verAlignMid">
                            <img class="classic-display img-18" src="../common/images/classic/icon_user.png" alt="<gsmsg:write key="cmn.staff" />">
                            <img class="original-display" src="../common/images/original/icon_user.png" alt="<gsmsg:write key="cmn.staff" />">
                            <bean:write name="addressData" property="adrName" />
                          </div><br>
                        </logic:iterate>
                      </div>
                      </logic:notEmpty>
                    </logic:iterate>
                  </logic:notEmpty>
                </td>
              </tr>
            </logic:equal>
            <!-- タイトル -->
            <tr>
              <th class="w20 txt_l"><gsmsg:write key="cmn.title" /></th>
              <td class="w80 <%= tdColor %>">
                <bean:define id="ttlColor" value=""/>
                <logic:notEqual name="sch041knForm" property="cmd" value="del">
                  <logic:equal name="sch041knForm" property="sch041Bgcolor" value="1">
                    <bean:define id="ttlColor" value="cl_fontSchTitleBlue"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Bgcolor" value="2">
                    <bean:define id="ttlColor" value="cl_fontSchTitleRed"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Bgcolor" value="3">
                    <bean:define id="ttlColor" value="cl_fontSchTitleGreen"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Bgcolor" value="4">
                    <bean:define id="ttlColor" value="cl_fontSchTitleYellow"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Bgcolor" value="5">
                    <bean:define id="ttlColor" value="cl_fontSchTitleBlack"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Bgcolor" value="6">
                    <bean:define id="ttlColor" value="cl_fontSchTitleNavy"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Bgcolor" value="7">
                    <bean:define id="ttlColor" value="cl_fontSchTitleWine"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Bgcolor" value="8">
                    <bean:define id="ttlColor" value="cl_fontSchTitleCien"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Bgcolor" value="9">
                    <bean:define id="ttlColor" value="cl_fontSchTitleGray"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Bgcolor" value="10">
                    <bean:define id="ttlColor" value="cl_fontSchTitleMarine"/>
                  </logic:equal>
                  <span class="<%=ttlColor %>"><bean:write name="sch041knForm" property="sch041Title" /></span>
                </logic:notEqual>

                <logic:equal name="sch041knForm" property="cmd" value="del">
                  <logic:equal name="sch041knForm" property="sch041knDelBgcolor" value="1">
                    <bean:define id="ttlColor" value="cl_fontSchTitleBlue"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelBgcolor" value="2">
                    <bean:define id="ttlColor" value="cl_fontSchTitleRed"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelBgcolor" value="3">
                    <bean:define id="ttlColor" value="cl_fontSchTitleGreen"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelBgcolor" value="4">
                    <bean:define id="ttlColor" value="cl_fontSchTitleYellow"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelBgcolor" value="5">
                    <bean:define id="ttlColor" value="cl_fontSchTitleBlack"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelBgcolor" value="6">
                    <bean:define id="ttlColor" value="cl_fontSchTitleNavy"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelBgcolor" value="7">
                    <bean:define id="ttlColor" value="cl_fontSchTitleWine"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelBgcolor" value="8">
                    <bean:define id="ttlColor" value="cl_fontSchTitleCien"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelBgcolor" value="9">
                    <bean:define id="ttlColor" value="cl_fontSchTitleGray"/>
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelBgcolor" value="10">
                    <bean:define id="ttlColor" value="cl_fontSchTitleMarine"/>
                  </logic:equal>
                  <span class="<%=ttlColor %>"><bean:write name="sch041knForm" property="sch041knDelTitle" /></span>
                </logic:equal>
              </td>
            </tr>

            <!-- 内容 -->
            <tr>
              <th class="w20 txt_l"><gsmsg:write key="cmn.content" /></th>
              <td class="w80 <%= tdColor %>">
                <logic:notEqual name="sch041knForm" property="cmd" value="del">
                  <bean:write name="sch041knForm" property="sch041knDspValue" filter="false" />
                </logic:notEqual>
                <logic:equal name="sch041knForm" property="cmd" value="del">
                  <bean:write name="sch041knForm" property="sch041knDelValue" filter="false" />
                </logic:equal>
              </td>
            </tr>
            <!-- 添付 -->
            <tr>
              <th class="w20 txt_l">
                <gsmsg:write key="cmn.attached" />
              </th>
              <td class="w80 <%= tdColor %>">
                <logic:notEmpty name="sch041knForm" property="sch041knFileList">
                  <logic:iterate id="bean" name="sch041knForm" property="sch041knFileList">
                    <div>
                      <a href="#!" onClick="return fileLinkClick(<bean:write name="bean" property="value"/>);"><bean:write name="bean" property="label"/></a>
                    </div>
                  </logic:iterate>
                </logic:notEmpty>
              </td>
            </tr>
            <!-- 備考 -->
            <tr>
              <th class="w20 txt_l"><gsmsg:write key="cmn.memo" /></th>
              <td class="w80 <%= tdColor %>">
                <logic:notEqual name="sch041knForm" property="cmd" value="del">
                  <bean:write name="sch041knForm" property="sch041knDspBiko" filter="false"/>
                </logic:notEqual>
                <logic:equal name="sch041knForm" property="cmd" value="del">
                  <bean:write name="sch041knForm" property="sch041knDelBiko" filter="false"/>
                </logic:equal>
              </td>
            </tr>
            <!-- 編集権限 -->
            <tr>
              <th class="w20 txt_l"><gsmsg:write key="cmn.edit.permissions" /></th>
              <td class="w80 <%= tdColor %>">
                <logic:notEqual name="sch041knForm" property="cmd" value="del">
                  <logic:equal name="sch041knForm" property="sch041Edit" value="0">
                    <gsmsg:write key="cmn.nolimit" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Edit" value="1">
                    <gsmsg:write key="cmn.only.principal.or.registant" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Edit" value="2">
                    <gsmsg:write key="cmn.only.affiliation.group.membership" />
                  </logic:equal>
                </logic:notEqual>
                <logic:equal name="sch041knForm" property="cmd" value="del">
                  <logic:equal name="sch041knForm" property="sch041knDelEdit" value="0">
                    <gsmsg:write key="cmn.nolimit" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelEdit" value="1">
                    <gsmsg:write key="cmn.only.principal.or.registant" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelEdit" value="2">
                    <gsmsg:write key="cmn.only.affiliation.group.membership" />
                  </logic:equal>
                </logic:equal>
              </td>
            </tr>
            <!-- リマインダー通知 -->
            <logic:equal name="sch041knForm" property="sch041ReminderEditMode.ableSelReminder" value="true">
            <tr>
              <th class="w20 txt_l">
                <gsmsg:write key="cmn.reminder" />
              </th>
              <td class="w80 <%= tdColor %>">
                <bean:write name="sch041knForm" property="sch041knTimeText" />
                <logic:notEmpty name="sch041knForm" property="sch041knTargetText">
                  <div>
                    <bean:write name="sch041knForm" property="sch041knTargetText" />
                  </div>
                </logic:notEmpty>
              </td>
            </tr>
            </logic:equal>
            <!-- 公開 -->
            <tr>
              <th class="w20 txt_l"><gsmsg:write key="schedule.21" /></th>
              <td class="w80 <%= tdColor %>">
                <logic:notEqual name="sch041knForm" property="cmd" value="del">
                  <logic:equal name="sch041knForm" property="sch041Public" value="0">
                    <gsmsg:write key="cmn.public" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Public" value="1">
                    <gsmsg:write key="cmn.private" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Public" value="2">
                    <gsmsg:write key="schedule.5" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Public" value="3">
                    <gsmsg:write key="schedule.28" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Public" value="4">
                    <gsmsg:write key="schedule.37" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041Public" value="5">
                    <gsmsg:write key="schedule.38" />
                  </logic:equal>
                </logic:notEqual>
                <logic:equal name="sch041knForm" property="cmd" value="del">
                  <logic:equal name="sch041knForm" property="sch041knDelPublic" value="0">
                    <gsmsg:write key="cmn.public" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelPublic" value="1">
                    <gsmsg:write key="cmn.private" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelPublic" value="2">
                    <gsmsg:write key="schedule.5" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelPublic" value="3">
                    <gsmsg:write key="schedule.28" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelPublic" value="4">
                    <gsmsg:write key="schedule.37" />
                  </logic:equal>
                  <logic:equal name="sch041knForm" property="sch041knDelPublic" value="5">
                    <gsmsg:write key="schedule.38" />
                  </logic:equal>
                </logic:equal>
              </td>
            </tr>
            <!-- 公開対象 -->
            <logic:equal name="sch041knForm" property="sch041Public" value="<%= String.valueOf(GSConstSchedule.DSP_USRGRP) %>">
            <tr>
              <th class="w20 txt_l">
                <gsmsg:write key="schedule.36" />
              </th>
              <td class="w80 <%= tdColor %>">
                <logic:notEmpty name="sch041knForm" property="sch041TargetList">
                  <logic:iterate id="usrMdl" name="sch041knForm" property="sch041TargetList">
                    <bean:write name="usrMdl" property="label" />
                    <br>
                  </logic:iterate>
                </logic:notEmpty>
              </td>
            </tr>
            </logic:equal>
            <!-- グループスケジュール 非表示部分 START -->
            <logic:notEqual name="sch041knForm" property="sch010SelectUsrKbn" value="1">
              <logic:notEmpty name="sch041knForm" property="sch041knSelectUsrList" scope="request">
                <!-- 同時登録 -->
                <tr>
                <th class="w20 txt_l"><gsmsg:write key="schedule.117" /></th>
                <td class="w80 <%= tdColor %>">
                  <logic:notEqual name="sch041knForm" property="cmd" value="del">
                  <span class="cl_fontWarn"><gsmsg:write key="schedule.140" /></span>
                  </logic:notEqual>
                  <logic:equal name="sch041knForm" property="cmd" value="del">
                  <span class="cl_fontWarn"><gsmsg:write key="schedule.141" /></span>
                  </logic:equal>
                  <logic:iterate id="selectUsrBean" name="sch041knForm" property="sch041knSelectUsrList" scope="request">
                  <br>
                  <logic:equal name="selectUsrBean" property="usrUkoFlg" value="1">
                    <span class="mukoUser">
                      <bean:write name="selectUsrBean" property="usiSei" scope="page"/>　<bean:write name="selectUsrBean" property="usiMei" scope="page"/>
                    </span>
                  </logic:equal>
                  <logic:notEqual name="selectUsrBean" property="usrUkoFlg" value="1">
                      <bean:write name="selectUsrBean" property="usiSei" scope="page"/>　<bean:write name="selectUsrBean" property="usiMei" scope="page"/>
                  </logic:notEqual>
                  </logic:iterate><br>
                </td>
                </tr>
              </logic:notEmpty>
            </logic:notEqual>
            <!-- グループスケジュール 非表示部分 END -->

            <!-- 施設予約 -->
            <logic:notEmpty name="sch041knForm" property="sch041knReserveList" scope="request">
              <tr>
                <th class="w20 txt_l"><gsmsg:write key="cmn.reserve" /></th>
                <td class="w80 <%= tdColor %>">
                  <logic:notEqual name="sch041knForm" property="cmd" value="del">
                    <span class="cl_fontWarn"><gsmsg:write key="schedule.142" /></span>
                  </logic:notEqual>
                  <logic:equal name="sch041knForm" property="cmd" value="del">
                    <span class="cl_fontWarn"><gsmsg:write key="schedule.143" /></span>
                  </logic:equal>
                  <logic:iterate id="selectResBean" name="sch041knForm" property="sch041knReserveList" scope="request">
                    <br><bean:write name="selectResBean" property="rsdName" scope="page"/>
                  </logic:iterate>
                  <br>
                </td>
              </tr>
            </logic:notEmpty>
            <!-- 登録者 -->
            <tr>
              <th class="w20 txt_l"><gsmsg:write key="cmn.registant" /></th>
              <td class="w80 <%= tdColor %>">
                <logic:notEqual name="sch041knForm" property="sch040AddUsrJkbn" value="9">
                  <logic:equal name="sch041knForm" property="sch040AddUsrUkoFlg" value="1">
                    <span class="mukoUser"><bean:write name="sch041knForm" property="sch040AddUsrName" /></span>
                  </logic:equal>
                  <logic:notEqual name="sch041knForm" property="sch040AddUsrUkoFlg" value="1">
                    <span><bean:write name="sch041knForm" property="sch040AddUsrName" /></span>
                  </logic:notEqual>
                </logic:notEqual>
                <logic:equal name="sch041knForm" property="sch040AddUsrJkbn" value="9">
                  <del>
                    <bean:write name="sch041knForm" property="sch040AddUsrName" />
                  </del>
                </logic:equal>
              </td>
            </tr>
          </table>
          <ul class="w100 verAlignMid">
            <li class="mrl_auto"></li>
            <li class="footerBtn_block">
              <logic:equal name="sch041knForm" property="cmd" value="add">
                <button type="button" class="baseBtn" value="<gsmsg:write key="man.final" />" onClick="buttonPush('041kn_commit', '<bean:write name="sch041knForm" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                  <gsmsg:write key="cmn.final" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back2" />" onClick="buttonPush('041kn_back', '<bean:write name="sch041knForm" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:equal>
              <logic:equal name="sch041knForm" property="cmd" value="edit">
                <button type="button" class="baseBtn" value="<gsmsg:write key="man.final" />" onClick="buttonPush('041kn_commit', '<bean:write name="sch041knForm" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                  <gsmsg:write key="cmn.final" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back2" />" onClick="buttonPush('041kn_back', '<bean:write name="sch041knForm" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:equal>
              <logic:equal name="sch041knForm" property="cmd" value="del">
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete2" />" onClick="buttonPush('041kn_del_ok', '<bean:write name="sch041knForm" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back2" />" onClick="buttonPush('041kn_back', 'edit');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:equal>
            </li>
          </ul>
        </div>
      </div>
    </html:form>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
  </body>
</html:html>