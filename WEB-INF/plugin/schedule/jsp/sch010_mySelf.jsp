<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<% int editConfOwn = Integer.valueOf(request.getParameter("editConfOwn"));%>
<% int editConfGroup = Integer.valueOf(request.getParameter("editConfGroup"));%>
<% int dspPublic = Integer.valueOf(request.getParameter("dspPublic"));%>
<% String project = request.getParameter("project");%>
<% String nippou = request.getParameter("nippou");%>
<% String grpHeight = request.getParameter("grpHeight");%>
<% long schTipCnt = Long.valueOf(request.getParameter("schTipCnt"));%>

<logic:iterate id="weekMdl" name="sch010Form" property="sch010TopList" scope="request" indexId="topCnt">
  <tr class="txt_l txt_t">
    <bean:define id="usrMdl" name="weekMdl" property="sch010UsrMdl"/>
    <!-- グループ -->
    <logic:equal name="usrMdl" property="usrKbn" value="1">
      <logic:equal name="sch010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
        <td class="w16 cal_colHeader" rowspan="<bean:write name="weekMdl" property="sch010PeriodRow" />">
      </logic:equal>
      <logic:notEqual name="sch010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
        <td class="w16 bgC_tableCell" rowspan="<bean:write name="weekMdl" property="sch010PeriodRow" />">
      </logic:notEqual>
      <span class="flo_l">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
      </span>
      <span class="fs_14 flo_l"><bean:write name="usrMdl" property="usrName" /></span><br>
      <span>
        <a href="#" onClick="moveSchedule('month', '-1', <bean:write name="usrMdl" property="usrKbn" />);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <gsmsg:write key="cmn.monthly" />
        </a><br>
        <a href="#" onClick="moveSchedule('list', '-1', <bean:write name="usrMdl" property="usrKbn" />);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_list.png" alt="<gsmsg:write key="cmn.list" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
          <span class="fs_13"><gsmsg:write key="cmn.list" /></span>
        </a>
      </span>
      </td>
    </logic:equal>
    <!-- 本人 -->
    <logic:notEqual name="usrMdl" property="usrKbn" value="1">
      <logic:equal name="sch010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
        <logic:equal name="usrMdl" property="zaisekiKbn" value="1">
          <td width="16%" rowspan="<bean:write name="weekMdl" property="sch010PeriodRow" />" class="cal_colHeader-zaiseki">
        </logic:equal>
        <logic:equal name="usrMdl" property="zaisekiKbn" value="2">
          <td width="16%" rowspan="<bean:write name="weekMdl" property="sch010PeriodRow" />" class="cal_colHeader-huzai">
        </logic:equal>
        <logic:equal name="usrMdl" property="zaisekiKbn" value="0">
          <td width="16%" rowspan="<bean:write name="weekMdl" property="sch010PeriodRow" />" class="cal_colHeader-sonota">
        </logic:equal>
      </logic:equal>
      <logic:notEqual name="sch010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
        <td width="16%" rowspan="<bean:write name="weekMdl" property="sch010PeriodRow" />" class="bgC_tableCell">
      </logic:notEqual>
      <%-- ユーザ・グループ名 --%>
      <a href="#!" onClick="openUserInfoWindow(<bean:write name="usrMdl" property="usrSid" />);">
        <span class="fs_14"><bean:write name="usrMdl" property="usrName" /></span>
      </a>
      <span class="cl_fontSchTitleBlack fs_13"><bean:write name="usrMdl" property="zaisekiMsg" /></span><br>
      <a href="#" onClick="moveSchedule('month', <bean:write name="usrMdl" property="usrSid" />, <bean:write name="usrMdl" property="usrKbn" />, '<bean:write name="sch010Form" property="sysDfGroupSid" />');">
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
      </td>
    </logic:notEqual>
    <!-- スケジュール情報 -->
    <logic:notEmpty name="weekMdl" property="sch010SchList">
      <logic:iterate id="dayMdl" name="weekMdl" property="sch010SchList" type="jp.groupsession.v2.sch.sch010.Sch010DayOfModel">
        <%
          String detailId = dayMdl.getSchDate() + "-";
          if (dayMdl.getUsrKbn() == 1) {
            detailId += "G";
          }
          detailId += dayMdl.getUsrSid();
        %>
        <logic:equal name="dayMdl" property="weekKbn" value="1">
          <logic:notEqual name="dayMdl" property="todayKbn" value="1">
            <td class="w12 bgC_tableCell_Sunday txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
              <input type="hidden" name="sch010CellClass" value="bgC_tableCell_Sunday" id="sch010BaseClass_<%= detailId %>">
          </logic:notEqual>
          <logic:equal name="dayMdl" property="todayKbn" value="1">
            <td class="w12 bgC_tableCell txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
          </logic:equal>
        </logic:equal>
        <logic:equal name="dayMdl" property="weekKbn" value="7">
          <logic:notEqual name="dayMdl" property="todayKbn" value="1">
            <td class="w12 bgC_tableCell_Saturday txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
              <input type="hidden" name="sch010CellClass" value="bgC_tableCell_Saturday" id="sch010BaseClass_<%= detailId %>">
          </logic:notEqual>
          <logic:equal name="dayMdl" property="todayKbn" value="1">
            <td  class="w12 bgC_tableCell txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="dayMdl" property="weekKbn" value="1">
          <logic:notEqual name="dayMdl" property="weekKbn" value="7">
            <logic:equal name="dayMdl" property="todayKbn" value="1">
              <td class="w12 bgC_tableCell txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
            </logic:equal>
            <logic:notEqual name="dayMdl" property="todayKbn" value="1">
              <td class="w12 bgC_tableCell txt_t <%= grpHeight %>" id="sch010Cell_<%= detailId %>">
            </logic:notEqual>
          </logic:notEqual>
        </logic:notEqual>
        <logic:equal name="usrMdl" property="schRegistFlg" value="true">
          <a href="#" class="js_schAddBtn" onClick="return addSchedule('add', <bean:write name="dayMdl" property="schDate" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="dayMdl" property="usrKbn" />);">
            <img class="btn_classicImg-display eventImg" src="../common/images/classic/icon_scadd.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display eventImg" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          </a>
          <span class="js_easyRegister js_schAddBtn cursor_pointer">
            <img class="eventImg wp18  ml5" src="../common/images/original/bubble_pen_icon.png" alt="<gsmsg:write key="cmn.add" />">
            <div class="display_none js_schDate"><bean:write name="dayMdl" property="schDate" /></div>
            <div class="display_none js_schUsrSid"><bean:write name="dayMdl" property="usrSid" /></div>
            <div class="display_none js_schUsrKbn"><bean:write name="dayMdl" property="usrKbn" /></div>
          </span>
          <html:multibox name="sch010Form" property="schIkkatuTorokuKey" onclick="sch010IkkatsuCheck(this)" styleClass="js_schIkkatsuCheck display_none">
            <%= detailId %>
          </html:multibox>
        </logic:equal>
        <logic:notEmpty name="dayMdl" property="holidayName">
          <bean:define id="grpSid" name="sch010Form" property="sch010DspGpSid" />
          <%
            if (!(String.valueOf(grpSid).substring(0, 1).equals("H")) && topCnt == 0) {
          %>
                <span class="ml5 flo_r fs_11 cl_fontWarn lh100 mt5"><bean:write name="dayMdl" property="holidayName" /></span>
          <%
            }
          %>
        </logic:notEmpty>
        <logic:notEmpty name="dayMdl" property="schDataList">
          <logic:iterate id="schMdl" name="dayMdl" property="schDataList">
            <bean:define id="u_usrsid" name="dayMdl" property="usrSid" type="java.lang.Integer" />
            <bean:define id="u_schsid" name="schMdl" property="schSid" type="java.lang.Integer" />
            <bean:define id="u_date" name="dayMdl" property="schDate"  type="java.lang.String" />
            <bean:define id="u_public" name="schMdl" property="public"  type="java.lang.Integer" />
            <bean:define id="u_kjnEdKbn" name="schMdl" property="kjnEdKbn"  type="java.lang.Integer" />
            <bean:define id="u_grpEdKbn" name="schMdl" property="grpEdKbn"  type="java.lang.Integer" />
            <%
              int publicType = ((Integer)pageContext.getAttribute("u_public",PageContext.PAGE_SCOPE));
              int kjnEdKbn = ((Integer)pageContext.getAttribute("u_kjnEdKbn",PageContext.PAGE_SCOPE));
              int grpEdKbn = ((Integer)pageContext.getAttribute("u_grpEdKbn",PageContext.PAGE_SCOPE));
            %>
            <!--公開-->
            <%
              if ((publicType == dspPublic) || (kjnEdKbn == editConfOwn || grpEdKbn == editConfGroup)) {
            %>
            <logic:empty name="schMdl" property="valueStr">
              <logic:equal name="schMdl" property="userKbn" value="0">
                <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="schMdl" property="schSid" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="schMdl" property="userKbn" />);">
              </logic:equal>
              <logic:notEqual name="schMdl" property="userKbn" value="0">
                <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="schMdl" property="schSid" />, <bean:write name="schMdl" property="userSid" />, <bean:write name="schMdl" property="userKbn" />);">
              </logic:notEqual>
              <span class="tooltips">
                <bean:write name="schMdl" property="title" />
              </span>
              <div class="cal_space">
            </logic:empty>
            <logic:notEmpty name="schMdl" property="valueStr">
              <bean:define id="scnaiyou" name="schMdl" property="valueStr" />
              <%
                String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
              %>
              <logic:equal name="schMdl" property="userKbn" value="0">
                <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="schMdl" property="schSid" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="schMdl" property="userKbn" />);">
              </logic:equal>
              <logic:notEqual name="schMdl" property="userKbn" value="0">
                <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="schMdl" property="schSid" />, <bean:write name="schMdl" property="userSid" />, <bean:write name="schMdl" property="userKbn" />);">
              </logic:notEqual>
              <span class="tooltips">
                <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
              </span>
              <div class="cal_space">
            </logic:notEmpty>
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
                <logic:equal name="dayMdl" property="usrKbn" value="0">
                  <logic:equal name="schMdl" property="userKbn" value="1">
                    <span class="cal_time no_w">
                      <span class="cal_label-g classic-display">G</span>
                      <span class="cal_label-g original-display"></span>
                  </logic:equal>
                  <logic:notEqual name="schMdl" property="userKbn" value="1">
                    <span class="cal_time no_w">
                  </logic:notEqual>
                </logic:equal>
                <logic:notEqual name="dayMdl" property="usrKbn" value="0">
                  <span class="cal_time no_w">
                </logic:notEqual>
                <bean:write name="schMdl" property="time" />
                </span>
              </div>
            </logic:notEmpty>
            <logic:empty name="schMdl" property="time">
              <logic:equal name="dayMdl" property="usrKbn" value="0">
                <logic:equal name="schMdl" property="userKbn" value="1">
                  <span class="cal_label-g classic-display">G</span>
                  <span class="cal_label-g original-display"></span>
                </logic:equal>
                <logic:notEqual name="schMdl" property="userKbn" value="1">
                  <div class="mt10"></div>
                </logic:notEqual>
              </logic:equal>
              <logic:notEqual name="dayMdl" property="usrKbn" value="0">
                <div class="mt10"></div>
              </logic:notEqual>
            </logic:empty>
            <div class="cal_content">
              <logic:equal name="schMdl" property="publicIconFlg" value="true">
                 <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                 <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
              </logic:equal>
              <bean:write name="schMdl" property="title" />
            </div>
            </div>
            </div>
            </a>
            <%
               } else {
            %>
            <!--非公開-->
            <span class="fs_13">
              <logic:notEmpty name="schMdl" property="time">
                <div class="cal_content-space">
                  <logic:equal name="dayMdl" property="usrKbn" value="0">
                    <logic:equal name="schMdl" property="userKbn" value="1">
                      <span class="cal_time no_w">
                      <span class="cal_label-g classic-display">G</span>
                      <span class="cal_label-g original-display"></span>
                    </logic:equal>
                    <logic:notEqual name="schMdl" property="userKbn" value="1">
                      <span class="cal_time no_w">
                    </logic:notEqual>
                  </logic:equal>
                  <logic:notEqual name="dayMdl" property="usrKbn" value="0">
                    <span class="cal_time no_w">
                  </logic:notEqual>
                  <bean:write name="schMdl" property="time" />
                  </span>
                </div>
              </logic:notEmpty>
              <logic:empty name="schMdl" property="time">
                <logic:equal name="dayMdl" property="usrKbn" value="0">
                  <logic:equal name="schMdl" property="userKbn" value="1">
                    <span class="cal_label-g classic-display">G</span>
                    <span class="cal_label-g original-display"></span>
                  </logic:equal>
                  <logic:notEqual name="schMdl" property="userKbn" value="1">
                    <div class="mt10"></div>
                  </logic:notEqual>
                </logic:equal>
                <logic:notEqual name="dayMdl" property="usrKbn" value="0">
                  <div class="mt10"></div>
                </logic:notEqual>
              </logic:empty>
              <div class="cal_content">
                <logic:equal name="schMdl" property="publicIconFlg" value="true">
                   <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                   <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                </logic:equal>
                <bean:write name="schMdl" property="title" />
              </div>
            </span>
            <logic:empty name="schMdl" property="time">
              <logic:equal name="dayMdl" property="usrKbn" value="0">
                <logic:equal name="schMdl" property="userKbn" value="1">
                  <span class="cal_label-g classic-display">G</span>
                  <span class="cal_label-g original-display"></span>
                </logic:equal>
              </logic:equal>
            </logic:empty>
            <div class="cal_content">
              <bean:write name="schMdl" property="title" />
            </div>
            <%
              }
            %>
          </logic:iterate>
        </logic:notEmpty>
      </logic:iterate>
    </logic:notEmpty>
  </tr>
  <!--期間スケジュール-->
  <logic:notEmpty name="weekMdl" property="sch010NoTimeSchList">
    <logic:iterate id="periodList" name="weekMdl" property="sch010NoTimeSchList">
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
                <%
                  if ((publicType == dspPublic) || (kjnEdKbn == editConfOwn || grpEdKbn == editConfGroup)) {
                %>
                <logic:empty name="uPeriodMdl" property="schAppendUrl">
                  <logic:empty name="uPeriodMdl" property="valueStr">
                    <logic:equal name="uPeriodMdl" property="userKbn" value="0">
                      <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="uPeriodMdl" property="schSid" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="uPeriodMdl" property="userKbn" />);">
                    </logic:equal>
                    <logic:notEqual name="uPeriodMdl" property="userKbn" value="0">
                      <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="uPeriodMdl" property="schSid" />, <bean:write name="uPeriodMdl" property="userSid" />, <bean:write name="uPeriodMdl" property="userKbn" />);">
                    </logic:notEqual>
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
                    <logic:equal name="uPeriodMdl" property="userKbn" value="0">
                      <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="uPeriodMdl" property="schSid" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="uPeriodMdl" property="userKbn" />);">
                    </logic:equal>
                    <logic:notEqual name="uPeriodMdl" property="userKbn" value="0">
                      <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="uPeriodMdl" property="schSid" />, <bean:write name="uPeriodMdl" property="userSid" />, <bean:write name="uPeriodMdl" property="userKbn" />);">
                    </logic:notEqual>
                    <span class="tooltips">
                      <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                    </span>
                  </logic:notEmpty>
                </logic:empty>
                <logic:notEmpty name="uPeriodMdl" property="schAppendUrl">
                  <logic:empty name="uPeriodMdl" property="valueStr">
                    <a href="<bean:write name="uPeriodMdl" property="schAppendUrl" />" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>">
                    <% boolean schFilter = true; %>
                    <logic:equal name="uPeriodMdl" property="userKbn" value="<%= project %>">
                      <% schFilter = false; %>
                    </logic:equal>
                    <span class="tooltips">
                      <bean:write name="uPeriodMdl" property="title" filter="<%= schFilter %>"/>
                    </span>
                  </logic:empty>
                  <logic:notEmpty name="uPeriodMdl" property="valueStr">
                    <bean:define id="scnaiyou" name="uPeriodMdl" property="valueStr" />
                    <%
                      String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                      String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                    %>
                    <a href="<bean:write name="uPeriodMdl" property="schAppendUrl" />" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>">
                    <span class="tooltips">
                      <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                    </span>
                  </logic:notEmpty>
                </logic:notEmpty>
                <!--タイトルカラー設定-->
                <logic:equal name="uPeriodMdl" property="bgColor" value="0">
                  <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                </logic:equal>
                <logic:equal name="uPeriodMdl" property="bgColor" value="1">
                  <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                </logic:equal>
                <logic:equal name="uPeriodMdl" property="bgColor" value="2">
                  <div class="cl_fontSchTitleRed opacity6-hover fs_13">
                </logic:equal>
                <logic:equal name="uPeriodMdl" property="bgColor" value="3">
                  <div class="cl_fontSchTitleGreen opacity6-hover fs_13">
                </logic:equal>
                <logic:equal name="uPeriodMdl" property="bgColor" value="4">
                  <div class="cl_fontSchTitleYellow opacity6-hover fs_13">
                </logic:equal>
                <logic:equal name="uPeriodMdl" property="bgColor" value="5">
                  <div class="cl_fontSchTitleBlack opacity6-hover fs_13">
                </logic:equal>
                <logic:equal name="uPeriodMdl" property="bgColor" value="6">
                  <div class="cl_fontSchTitleNavy opacity6-hover fs_13">
                </logic:equal>
                <logic:equal name="uPeriodMdl" property="bgColor" value="7">
                  <div class="cl_fontSchTitleWine opacity6-hover fs_13">
                </logic:equal>
                <logic:equal name="uPeriodMdl" property="bgColor" value="8">
                  <div class="cl_fontSchTitleCien opacity6-hover fs_13">
                </logic:equal>
                <logic:equal name="uPeriodMdl" property="bgColor" value="9">
                  <div class="cl_fontSchTitleGray opacity6-hover fs_13">
                </logic:equal>
                <logic:equal name="uPeriodMdl" property="bgColor" value="10">
                  <div class="cl_fontSchTitleMarine opacity6-hover fs_13">
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
                    <font size="-2"><bean:write name="uPeriodMdl" property="time" /><br></font>
                  </logic:notEmpty>
                  <logic:equal name="uPeriodMdl" property="publicIconFlg" value="true">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                  </logic:equal>
                  <bean:write name="uPeriodMdl" property="title" filter="<%= schFilter %>" />
                </div>
                <%
                  } else {
                %>
                <!--非公開-->
                <span class="fs_13">
                  <logic:equal name="dayMdl" property="usrKbn" value="0">
                    <logic:equal name="uPeriodMdl" property="userKbn" value="1">
                      <span class="cal_label-g classic-display">G</span>
                      <span class="cal_label-g original-display"></span>
                    </logic:equal>
                  </logic:equal>
                  <logic:notEmpty name="uPeriodMdl" property="time">
                    <font size="-2"><bean:write name="uPeriodMdl" property="time" /><br></font>
                  </logic:notEmpty>
                  <logic:equal name="uPeriodMdl" property="publicIconFlg" value="true">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                  </logic:equal>
                  <bean:write name="uPeriodMdl" property="title" />
                </span>
                <%
                  }
                %>
                </div>
                </a>
                </div>
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
</logic:iterate>