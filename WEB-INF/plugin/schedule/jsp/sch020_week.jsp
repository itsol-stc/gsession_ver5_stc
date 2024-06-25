<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%
  String sunday            = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.CHANGE_WEEK_SUN);
  String monday            = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.CHANGE_WEEK_MON);
  String tuesday           = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.CHANGE_WEEK_TUE);
  String wednesday         = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.CHANGE_WEEK_WED);
  String thursday          = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.CHANGE_WEEK_THU);
  String friday            = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.CHANGE_WEEK_FRI);
  String saturday          = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.CHANGE_WEEK_SAT);
%>

<tr class="cal_month_dw">
  <logic:equal name="sch020Form" property="sch020DspStartWeek" value="<%= sunday %>">
  <%-- 開始曜日：日曜日 --%>
    <td class="no_w cal_header bgC_calSunday border_top_none">
      <span class="cl_fontSunday fw_b">
        <gsmsg:write key="cmn.sunday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.Monday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.tuesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.wednesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.thursday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.friday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSaturday border_top_none">
      <span class="cl_fontSaturday fw_b">
        <gsmsg:write key="cmn.saturday" />
      </span>
    </td>
  </logic:equal>

  <logic:equal name="sch020Form" property="sch020DspStartWeek" value="<%= monday %>">
  <%-- 開始曜日：月曜日 --%>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.Monday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.tuesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.wednesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.thursday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.friday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSaturday border_top_none">
      <span class="cl_fontSaturday fw_b">
        <gsmsg:write key="cmn.saturday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSunday border_top_none">
      <span class="cl_fontSunday fw_b">
        <gsmsg:write key="cmn.sunday" />
      </span>
    </td>
  </logic:equal>

  <logic:equal name="sch020Form" property="sch020DspStartWeek" value="<%= tuesday %>">
  <%-- 開始曜日：火曜日 --%>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.tuesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.wednesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.thursday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.friday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSaturday border_top_none">
      <span class="cl_fontSaturday fw_b">
        <gsmsg:write key="cmn.saturday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSunday border_top_none">
      <span class="cl_fontSunday fw_b">
        <gsmsg:write key="cmn.sunday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.Monday" />
      </span>
    </td>
  </logic:equal>

  <logic:equal name="sch020Form" property="sch020DspStartWeek" value="<%= wednesday %>">
  <%-- 開始曜日：水曜日 --%>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.wednesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.thursday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.friday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSaturday border_top_none">
      <span class="cl_fontSaturday fw_b">
        <gsmsg:write key="cmn.saturday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSunday border_top_none">
      <span class="cl_fontSunday fw_b">
        <gsmsg:write key="cmn.sunday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.Monday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.tuesday" />
      </span>
    </td>
  </logic:equal>

  <logic:equal name="sch020Form" property="sch020DspStartWeek" value="<%= thursday %>">
  <%-- 開始曜日：木曜日 --%>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.thursday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.friday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSaturday border_top_none">
      <span class="cl_fontSaturday fw_b">
        <gsmsg:write key="cmn.saturday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSunday border_top_none">
      <span class="cl_fontSunday fw_b">
        <gsmsg:write key="cmn.sunday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.Monday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.tuesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.wednesday" />
      </span>
    </td>
  </logic:equal>

  <logic:equal name="sch020Form" property="sch020DspStartWeek" value="<%= friday %>">
  <%-- 開始曜日：金曜日 --%>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.friday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSaturday border_top_none">
      <span class="cl_fontSaturday fw_b">
        <gsmsg:write key="cmn.saturday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSunday border_top_none">
      <span class="cl_fontSunday fw_b">
        <gsmsg:write key="cmn.sunday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.Monday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.tuesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.wednesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.thursday" />
      </span>
    </td>
  </logic:equal>

  <logic:equal name="sch020Form" property="sch020DspStartWeek" value="<%= saturday %>">
  <%-- 開始曜日：土曜日 --%>
    <td class="no_w cal_header bgC_calSaturday border_top_none">
      <span class="cl_fontSaturday fw_b">
        <gsmsg:write key="cmn.saturday" />
      </span>
    </td>
    <td class="no_w cal_header bgC_calSunday border_top_none">
      <span class="cl_fontSunday fw_b">
        <gsmsg:write key="cmn.sunday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.Monday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.tuesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.wednesday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.thursday" />
      </span>
    </td>
    <td class="no_w cal_header border_top_none">
      <span class="cl_fontBody fw_b">
        <gsmsg:write key="cmn.friday" />
      </span>
    </td>
  </logic:equal>
</tr>