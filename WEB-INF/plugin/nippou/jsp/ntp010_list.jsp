<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.ntp.ntp010.Ntp010Form"%>
<%@ page import="jp.groupsession.v2.ntp.ntp010.Ntp010WeekOfModel"%>
<% String chkClass = "ntp_chk"; %>
<% long ntpTipCnt = 0; %>

<logic:iterate id="gpWeekMdl" name="ntp010Form" property="ntp010BottomList" scope="request" indexId="cnt">
  <bean:define id="ret" value="<%= String.valueOf(cnt.intValue() % 5) %>" />
  <logic:notEqual name="cnt" value="0" scope="page">
    <logic:equal name="ret" value="0">
      <%-- 5行毎にタイトル(氏名,日付) --%>
      <tr class="calendar_weekHeader">
        <td class="cal_header fw_b w16 border_top_none">
          <gsmsg:write key="cmn.name" />
        </td>
        <logic:notEmpty name="ntp010Form" property="ntp010CalendarList" scope="request">
          <logic:iterate id="calBean" name="ntp010Form" property="ntp010CalendarList" scope="request">
            <bean:define id="tdColor" value="" />
            <% String[] tdColors = new String[] {"cal_header", "bgC_select","bgC_calSaturday" ,"bgC_calSunday"};
               String clFont = "";
            %>
            <logic:equal name="calBean" property="todayKbn" value="1">
              <% tdColor = tdColors[1]; %>
            </logic:equal>
            <logic:notEqual name="calBean" property="todayKbn" value="1">
             <logic:equal name="calBean" property="holidayKbn" value="1">
               <% tdColor = tdColors[0];
                  clFont = "cl_fontSunday";
               %>
             </logic:equal>
            <logic:notEqual name="calBean" property="holidayKbn" value="1">
              <logic:equal name="calBean" property="weekKbn" value="1">
              <% tdColor = tdColors[3];
                 clFont = "cl_fontSunday";
              %>
              </logic:equal>
              <logic:equal name="calBean" property="weekKbn" value="7">
                <% tdColor = tdColors[2];
                   clFont = "cl_fontSaturday";
                %>
              </logic:equal>
              <logic:notEqual name="calBean" property="weekKbn" value="1">
                <logic:notEqual name="calBean" property="weekKbn" value="7">
                   <% tdColor = tdColors[0]; %>
                </logic:notEqual>
              </logic:notEqual>
            </logic:notEqual>
            </logic:notEqual>
            <bean:define id="rkyKbn" name="calBean" property="rokuyoKbn" />
            <%
              String rkyName = "";
              if (rkyKbn.equals(GSConst.RKY_KBN_SENSHOU)) {
                  rkyName = "senshou";
              } else if (rkyKbn.equals(GSConst.RKY_KBN_TOMOBIKI)) {
                  rkyName = "tomobiki";
              } else if (rkyKbn.equals(GSConst.RKY_KBN_SENBU)) {
                  rkyName = "senbu";
              } else if (rkyKbn.equals(GSConst.RKY_KBN_BUTSUMETSU)) {
                  rkyName = "butsumetsu";
              } else if (rkyKbn.equals(GSConst.RKY_KBN_TAIAN)) {
                  rkyName = "taian";
              } else if (rkyKbn.equals(GSConst.RKY_KBN_SHAKKOU)) {
                  rkyName = "shakkou";
              }
            %>
            <td class="no_w <%= tdColor %> w12 border_top_none">
              <div class="calendar_dayHeader <%= rkyName %>">
                <span class="fw_b <%= clFont %>">
                  <bean:write name="calBean" property="dspDayString" />
                </span>
              </div>
            </td>
          </logic:iterate>
        </logic:notEmpty>
      </tr>
    </logic:equal>
  </logic:notEqual>
  <tr class="txt_l txt_t">
    <bean:define id="usrMdl" name="gpWeekMdl" property="ntp010UsrMdl"/>
    <logic:notEqual name="usrMdl" property="usrKbn" value="1">
      <logic:equal name="ntp010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_USE) %>">
        <logic:equal name="usrMdl" property="zaisekiKbn" value="1">
          <td class="cal_colHeader-zaiseki w16">
        </logic:equal>
        <logic:equal name="usrMdl" property="zaisekiKbn" value="2">
          <td class="cal_colHeader-huzai w16">
        </logic:equal>
        <logic:equal name="usrMdl" property="zaisekiKbn" value="0">
          <td class="cal_colHeader-sonota w16">
        </logic:equal>
      </logic:equal>
      <logic:notEqual name="ntp010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_USE) %>">
        <td class="bgC_tableCell w16">
      </logic:notEqual>
      <%-- ユーザ・グループ名 --%>
      <a href="#!" onClick="openUserInfoWindow(<bean:write name="usrMdl" property="usrSid" />);">
        <logic:equal name="usrMdl" property="usrUkoFlg" value="1">
          <span class="fs_14 mukoUser"><bean:write name="usrMdl" property="usrName" /></span>
        </logic:equal>
        <logic:notEqual name="usrMdl" property="usrUkoFlg" value="1">
          <span class="fs_14"><bean:write name="usrMdl" property="usrName" /></span>
        </logic:notEqual>
      </a>
      <span class="cl_fontNtpTitleBlack fs_13"><bean:write name="usrMdl" property="zaisekiMsg" /></span><br>
      <a class="fs_12" href="#" onClick="moveMonthNippou('month', <bean:write name="usrMdl" property="usrSid" />, <bean:write name="usrMdl" property="usrKbn" />);">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
        <gsmsg:write key="cmn.monthly" />
      </a><br>
      <a class="fs_12" href="#" onClick="moveListNippou('list', <bean:write name="usrMdl" property="usrSid" />, <bean:write name="usrMdl" property="usrKbn" />);">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_list.png" alt="<gsmsg:write key="cmn.list" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
        <gsmsg:write key="cmn.list" />
      </a><br>
      <logic:equal name="ntp010Form" property="smailUseOk" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_USE) %>">
        <logic:equal name="usrMdl" property="smlAble" value="1">
          <a href="#" class="js_smlSendLink fs_12" id="<bean:write name="usrMdl" property="usrSid" />">
            <img class="btn_classicImg-display wp16hp18" src="../common/images/classic/icon_smail.gif" alt="<gsmsg:write key="cmn.shortmail" />">
            <img class="btn_originalImg-display wp16hp18" src="../schedule/images/original/plugin_smail.png" alt="<gsmsg:write key="cmn.shortmail" />">
            <gsmsg:write key="cmn.shortmail" />
          </a><br>
        </logic:equal>
      </logic:equal>
      <logic:equal name="ntp010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_USE) %>">
        <div class="no_w">
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
            <option value="ntp010Zaiseki" <%= zaisekiSelected %>><gsmsg:write key="cmn.zaiseki" /></option>
            <option value="ntp010Fuzai" <%= fuzaiSelected %>><gsmsg:write key="cmn.absence" /></option>
            <option value="ntp010Sonota" <%= sonotaSelected %>><gsmsg:write key="cmn.other" /></option>
          </select>
        </div>
      </logic:equal>
      </td>
      <%-- 日報情報 --%>
      <logic:notEmpty name="gpWeekMdl" property="ntp010NtpList">
        <logic:iterate id="gpDayMdl" name="gpWeekMdl" property="ntp010NtpList">
          <logic:equal name="gpDayMdl" property="weekKbn" value="1">
            <td class="w12 bgC_tableCell_Sunday txt_t">
          </logic:equal>
          <logic:equal name="gpDayMdl" property="weekKbn" value="7">
            <td class="w12 bgC_tableCell_Saturday txt_t">
          </logic:equal>
          <logic:notEqual name="gpDayMdl" property="weekKbn" value="1">
            <logic:notEqual name="gpDayMdl" property="weekKbn" value="7">
                <td class="w12 bgC_tableCell txt_t">
            </logic:notEqual>
          </logic:notEqual>
          <logic:equal name="ntp010Form" property="adminKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.USER_ADMIN) %>">
            <a href="#" onClick="return addNippou('add', <bean:write name="gpDayMdl" property="ntpDate" />, <bean:write name="gpDayMdl" property="usrSid" />, <bean:write name="gpDayMdl" property="usrKbn" />);">
              <img class="btn_classicImg-display eventImg" src="../nippou/images/classic/icon_add_nippou.gif" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display eventImg" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            </a>
          </logic:equal>
          <logic:notEmpty name="gpDayMdl" property="ntpDataList">
            <logic:iterate id="ntpMdl" name="gpDayMdl" property="ntpDataList">
              <bean:define id="chKbn" name="ntpMdl" property="ntp_chkKbn" type="java.lang.Integer" />
              <% chkClass = "ntp_chk"; %>
              <% if (chKbn == 1) { %>
                <div>
              <% } else { %>
                <div class="ntp_checkContent bgC_lightGray">
              <% } %>
              <bean:define id="u_usrsid" name="gpDayMdl" property="usrSid" type="java.lang.Integer" />
              <bean:define id="u_ntpsid" name="ntpMdl" property="ntpSid" type="java.lang.Integer" />
              <bean:define id="u_date" name="gpDayMdl" property="ntpDate"  type="java.lang.String" />
              <logic:empty name="ntpMdl" property="detail">
                <a href="#" id="tooltips_ntp<%= String.valueOf(ntpTipCnt++) %>" onClick="editNippou('edit', <bean:write name="gpDayMdl" property="ntpDate" />, <bean:write name="ntpMdl" property="ntpSid" />, <bean:write name="gpDayMdl" property="usrSid" />, 0);">
                  <span class="tooltips">
                    <bean:write name="ntpMdl" property="title" />
                  </span>
              </logic:empty>
              <logic:notEmpty name="ntpMdl" property="detail">
                <bean:define id="ntdetailu" name="ntpMdl" property="detail" />
                <a href="#" id="tooltips_ntp<%= String.valueOf(ntpTipCnt++) %>" onClick="editNippou('edit', <bean:write name="gpDayMdl" property="ntpDate" />, <bean:write name="ntpMdl" property="ntpSid" />, <bean:write name="gpDayMdl" property="usrSid" />, 0);">

                  <bean:define id="ntdetailu" name="ntpMdl" property="detail" />
                  <%
                    String tmpText = (String)pageContext.getAttribute("ntdetailu",PageContext.PAGE_SCOPE);
                    String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTmlPlusAmparsant(tmpText);
                  %>
                  <span class="tooltips">
                    <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                  </span>
              </logic:notEmpty>

              <div class="mt5 cl_linkDef">
                <%-- コメントアイコン表示  --%>
                <logic:notEqual name="ntpMdl" property="ntp_cmtCnt" value="0">
                  <span class="mr5">
                    <img class="btn_classicImg-display" src="../nippou/images/classic/icon_comment.gif" alt="<gsmsg:write key="cmn.preferences2" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_comment.png" alt="<gsmsg:write key="cmn.preferences2" />">
                    <bean:write name="ntpMdl" property="ntp_cmtCnt" />
                  </span>
                </logic:notEqual>
                <%-- いいねアイコン表示  --%>
                <logic:notEqual name="ntpMdl" property="ntp_goodCnt" value="0">
                  <img class="btn_classicImg-display" src="../nippou/images/classic/bg_good_2.gif" alt="<gsmsg:write key="cmn.preferences2" />">
                  <img class="btn_originalImg-display" src="../nippou/images/original/icon_good.png" alt="<gsmsg:write key="cmn.preferences2" />">
                  <bean:write name="ntpMdl" property="ntp_goodCnt" />
                </logic:notEqual>
              </div>

              <%--タイトルカラー設定--%>
              <logic:equal name="ntpMdl" property="titleColor" value="0">
                <div class="cl_fontNtpTitleBlue opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="ntpMdl" property="titleColor" value="1">
                <div class="cl_fontNtpTitleBlue opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="ntpMdl" property="titleColor" value="2">
                <div class="cl_fontNtpTitleRed opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="ntpMdl" property="titleColor" value="3">
                <div class="cl_fontNtpTitleGreen opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="ntpMdl" property="titleColor" value="4">
                <div class="cl_fontNtpTitleYellow opacity6-hover fs_13">
              </logic:equal>
              <logic:equal name="ntpMdl" property="titleColor" value="5">
                <div class="cl_fontNtpTitleBlack opacity6-hover fs_13">
              </logic:equal>
              <logic:notEmpty name="ntpMdl" property="time">
                <div class="lh100">
                  <span class="cal_time">
                    <bean:write name="ntpMdl" property="time" />
                  </span>
                </div>
              </logic:notEmpty>
              <div class="cal_content">
                <bean:write name="ntpMdl" property="title" />
              </div>
              </div>
              </a>
              </div>
            </logic:iterate>
          </logic:notEmpty>
          </td>
        </logic:iterate>
      </logic:notEmpty>
    </logic:notEqual>
  </tr>
</logic:iterate>